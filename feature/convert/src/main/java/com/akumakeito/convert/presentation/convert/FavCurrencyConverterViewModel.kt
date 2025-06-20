package com.akumakeito.convert.presentation.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonmodels.usd
import com.akumakeito.commonui.presentation.ErrorType
import com.akumakeito.convert.domain.ConvertBaseCurrencyToFavCurrencyUseCase
import com.akumakeito.convert.domain.ConvertRepository
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.convert.presentation.convert.FavCurrencyConvertScreenModel
import com.akumakeito.core.appsettings.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    init {
        viewModelScope.launch {
            val lastSelectedBaseCurrencyShortCode =
                appSettingsRepository.getLastSelectedBaseCurrency()

            currencyRepository.getFavoriteCurrencyListFlow().collect { favList ->
                _state.update { model ->
                    model.copy(
                        from = favList.find { it.shortCode == lastSelectedBaseCurrencyShortCode }
                            ?: usd,
                        favCurrency = favList,
                        isLoading = false
                    )
                }
            }
        }
        _state
            .onEach { model ->
                val newList = model.favCurrency.filterNot { it == model.from }
                _state.update {
                    it.copy(
                        convertedToFavorites = newList,
                        isButtonEnable = it.amount != "0.0"
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
                else -> it.copy(
                    isError = ErrorType.SERVER,
                    isLoading = false
                )
            }

        }
    }

    fun onBackspaceClick() {
        _state.update {
            val newAmount = it.amount.dropLast(1)
            it.copy(
                amount = if (newAmount.isEmpty()) "0.0" else newAmount
            )
        }
    }

    fun onAmountChanged(value: String) {
        _state.update {
            it.copy(
                amount = if (it.amount == "0.0") value else it.amount + value
            )
        }
    }

    fun onClearClick() {
        _state.update {
            it.copy(
                amount = "0.0"
            )
        }
    }
}