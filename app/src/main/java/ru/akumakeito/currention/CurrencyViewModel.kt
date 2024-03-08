package ru.akumakeito.currention

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.model.SearchState
import ru.akumakeito.currention.repository.CurrencyRepository
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    init {
        getFiatCurrencies()
    }
    val _searchingState = MutableStateFlow(SearchState())
    val searchingState = _searchingState

    private val _fiatCurrencies = repository.fiatCurrencies
    val fiatCurrencies = _fiatCurrencies

    private val _popularFiatCurrencies = repository.fiatCurrencies.map {
        it.filter { fiatCurrency -> fiatCurrency.isPopular }
    }
    val popularFiatCurrencies = _popularFiatCurrencies


    fun getFiatCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        repository.getFiatCurrencyList()
    }

    fun clearFiatCurrencies() = viewModelScope.launch {
        repository.deleteAllFiat()
    }

    fun getPopularCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        repository.getPopularCurrencyList()
    }
}