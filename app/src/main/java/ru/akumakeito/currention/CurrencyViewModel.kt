package ru.akumakeito.currention

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.repository.CurrencyRepository
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private val _fiatCurrencies = mutableStateOf<List<FiatCurrency>>(emptyList())
    val fiatCurrencies = _fiatCurrencies


    fun getFiatCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        _fiatCurrencies.value = repository.getFiatCurrencyList()
    }

    fun clearFiatCurrencies() = viewModelScope.launch {
        repository.deleteAllFiat()
    }

    fun getPopularCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        repository.getPopularCurrencyList()
    }
}