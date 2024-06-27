package ru.akumakeito.currention.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.akumakeito.currention.domain.model.FiatCurrency
import ru.akumakeito.currention.domain.repository.CurrencyRepository
import ru.akumakeito.currention.domain.state.SearchState
import javax.inject.Inject


@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    init {
        getFiatCurrencies()
    }

    private val _searchingState = MutableStateFlow(SearchState())
    val searchingState = _searchingState.asStateFlow()

    private val _fiatCurrencies = repository.fiatCurrencies

    @OptIn(ExperimentalCoroutinesApi::class)
    val fiatCurrencies = _searchingState.flatMapLatest { state ->
        _fiatCurrencies.map { currencies ->

            if (state.searchText.isBlank()) {
                currencies.sortedByDescending { it.isPopular }.sortedByDescending { it.isFavorite }

            } else {
                currencies.filter { it.doesMatchSearchQuery(state.searchText) }
            }
        }
    }


    fun getFiatCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        repository.getFiatCurrencyList()
    }

    fun onSearchTextChange(text: String) {
        _searchingState.update {
            it.copy(searchText = text)
        }
    }


    fun updateFavoriteCurrency(currency: FiatCurrency) {
        Log.d("checkbox", "vm $currency")
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteCurrency(currency)
        }
    }


}
