package ru.akumakeito.currention.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.repository.PairCurrencyRepository
import ru.akumakeito.currention.util.Constants.Companion.convertingCurrency
import javax.inject.Inject


@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(
    private val pairCurrencyRepository: PairCurrencyRepository
) : ViewModel() {

    private val _convertingCurrencyState = MutableStateFlow(convertingCurrency)
    val convertingCurrencyState = _convertingCurrencyState.asStateFlow()

    init {
        convert(
            _convertingCurrencyState.value.firstCurrency,
            _convertingCurrencyState.value.secondCurrency,
            1
        )
        convert(
            _convertingCurrencyState.value.secondCurrency,
            _convertingCurrencyState.value.firstCurrency,
            1
        )


    }

    private fun convert(
        currencyFromShortCode: FiatCurrency,
        currencyToShortCode: FiatCurrency,
        amount: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                pairCurrencyRepository.convert(currencyFromShortCode, currencyToShortCode, amount)
            _convertingCurrencyState.update {
                if (result.from == it.firstCurrency.shortCode) {
                    it.copy(
                        rateFromFirstToSecond = result.value,
                    )
                } else {
                    it.copy(
                        rateFromSecondToFirst = result.value,
                    )
                }
            }
        }
    }

    fun updatePairCurrencyFrom(fromCurrency: FiatCurrency) {
        _convertingCurrencyState.update {
            it.copy(firstCurrency = fromCurrency)
        }
    }

    fun updatePairCurrencyTo(toCurrency: FiatCurrency) {
        _convertingCurrencyState.update {
            it.copy(secondCurrency = toCurrency)
        }
    }

}