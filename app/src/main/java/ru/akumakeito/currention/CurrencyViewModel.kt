package ru.akumakeito.currention

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _fiatCurrencies = _searchingState.flatMapLatest { searchState ->
        if (searchState.searchText.isBlank()) {
            repository.fiatCurrencies
        } else {
            repository.fiatCurrencies.map {
                it.filter { fiatCurrency ->
                    fiatCurrency.name.lowercase().contains(
                        searchState.searchText.lowercase()
                    ) || fiatCurrency.shortCode.contains(searchState.searchText.uppercase())
                }
            }
        }
    }

    val fiatCurrencies = _fiatCurrencies

    private val _popularFiatCurrencies = repository.fiatCurrencies.map {
        it.filter { fiatCurrency -> fiatCurrency.isPopular }
    }
    val popularFiatCurrencies = _popularFiatCurrencies


    fun getFiatCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        repository.getFiatCurrencyList()
    }

    fun onSearchTextChange(text: String) {
        _searchingState.update {
            it.copy(searchText = text)
        }
    }

    fun onToggleSearch() {
        _searchingState.update { searchState ->
            searchState.copy(isSearching = !searchState.isSearching)

            if (!searchState.isSearching) {
                searchState.copy(searchText = "")
            } else {
                searchState
            }

        }

        fun clearFiatCurrencies() = viewModelScope.launch {
            repository.deleteAllFiat()
        }

        fun getPopularCurrencies() = viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularCurrencyList()
        }
    }
}