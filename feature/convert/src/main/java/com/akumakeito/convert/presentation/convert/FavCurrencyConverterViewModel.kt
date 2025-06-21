package com.akumakeito.convert.presentation.convert

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akumakeito.convert.domain.ConvertBaseCurrencyToFavCurrencyUseCase
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.core.appsettings.AppSettingsRepository
import com.akumakeito.core.models.domain.FiatCurrency
import com.akumakeito.core.models.usd
import com.akumakeito.core.util.getErrorType
import com.akumakeito.core.util.log
import com.akumakeito.core.util.toDateTimeString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FavCurrencyConverterViewModel @Inject constructor(
    private val convertUseCase: ConvertBaseCurrencyToFavCurrencyUseCase,
    private val appSettingsRepository: AppSettingsRepository,
    private val currencyRepository: CurrencyRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        FavCurrencyConvertScreenModel(
            from = usd,
            amount = "0.0",
            convertedToFavorites = emptyList(),
            favCurrency = emptyList(),
        )
    )

    val state = _state.asStateFlow()
    val amount = MutableStateFlow("0.0")

    init {

        appSettingsRepository.getLastSelectedBaseCurrencyFlow()
            .flatMapLatest { base ->
                combine(
                    currencyRepository.getFavoriteCurrencyListFlow(),
                    appSettingsRepository.getLastRatesUpdate(base),
                    amount,
                ) { favList, lastUpdate, amount ->
                    ConvertInput(favList, base, lastUpdate, amount)
                }
            }
            .onEach { input ->
                val newList = input.favList.filterNot { it.shortCode == input.base }
                log("input ONEACH first: ${input}")
                _state.update { model ->
                    model.copy(
                        from = input.favList.find { it.shortCode == input.base }
                            ?: input.favList.first(),
                        favCurrency = input.favList,
                        isLoading = false,
                        lastUpdate = input.lastUpdate.toDateTimeString(),
                        convertedToFavorites = newList,
                        isButtonEnable = input.amount != "0.0",
                        amount = input.amount
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun selectBaseCurrency(currency: FiatCurrency) = viewModelScope.launch {
        _state.update {
            it.copy(
                from = currency
            )
        }
        appSettingsRepository.setLastSelectedBaseCurrency(currency.shortCode)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun convert() = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }
        val result = convertUseCase(
            _state.value.from,
            _state.value.convertedToFavorites,
            amountString = _state.value.amount
        )
        _state.update {
            when {
                result.isSuccess -> it.copy(
                    convertedToFavorites = result.getOrThrow(),
                    isLoading = false
                )

                else -> {
                    val errorType = getErrorType(result.exceptionOrNull())
                    it.copy(
                        error = errorType,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onBackspaceClick() {
        amount.update {
            val newAmount = it.dropLast(1)
            if (newAmount.isEmpty()) "0.0" else newAmount
        }
    }

    fun onAmountChanged(value: String) {
        amount.update {
            if (it == "0.0") value else it + value
        }
    }

    fun onClearClick() {
        amount.value = "0.0"
    }

    fun onCloseError() {
        _state.update {
            it.copy(
                error = null
            )
        }
    }
}