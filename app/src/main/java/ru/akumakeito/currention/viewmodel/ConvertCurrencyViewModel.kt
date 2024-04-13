package ru.akumakeito.currention.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.repository.PairCurrencyRepository
import ru.akumakeito.currention.util.Constants.Companion.newPair
import javax.inject.Inject


@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(
    private val pairCurrencyRepository: PairCurrencyRepository
) : ViewModel() {

    private val _convertingCurrencyState = MutableStateFlow(newPair)
    val convertingCurrencyState = _convertingCurrencyState.asStateFlow()

    init {
        convert(_convertingCurrencyState.value.fromCurrency, _convertingCurrencyState.value.toCurrency, 1)
    }

    private fun convert(
        currencyFromShortCode: FiatCurrency,
        currencyToShortCode: FiatCurrency,
        amount: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            pairCurrencyRepository.convert(currencyFromShortCode, currencyToShortCode, amount)
        }
    }

}