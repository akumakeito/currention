package ru.akumakeito.currention.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.akumakeito.currention.model.ConvertingCurrency
import ru.akumakeito.currention.repository.PairCurrencyRepository
import ru.akumakeito.currention.util.Constants.Companion.rub
import ru.akumakeito.currention.util.Constants.Companion.usd
import javax.inject.Inject

val convertingCurrency = ConvertingCurrency(usd, rub, 1, 1.0)
@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(
    private val pairCurrencyRepository: PairCurrencyRepository
) : ViewModel() {

    private val _convertingCurrencyState = MutableStateFlow(convertingCurrency)
    val convertingCurrencyState = _convertingCurrencyState.asStateFlow()

}