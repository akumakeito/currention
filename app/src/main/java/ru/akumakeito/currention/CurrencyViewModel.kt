package ru.akumakeito.currention

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
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

    private val _searchingState = MutableStateFlow(SearchState())
    val searchingState = _searchingState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _fiatCurrencies = repository.fiatCurrencies

    @OptIn(ExperimentalCoroutinesApi::class)
    val fiatCurrencies = _searchingState.flatMapLatest { state ->

        if (state.searchText.isBlank()) {
            _fiatCurrencies.map {
                it.sortedByDescending { it.isPopular }
            }
        } else {
            _fiatCurrencies.map {
                it.filter { it.doesMatchSearchQuery(state.searchText) }
            }
        }
    }


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
    }

    fun clearFiatCurrencies() = viewModelScope.launch {
        repository.deleteAllFiat()
    }

    fun getPopularCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        repository.getPopularCurrencyList()
    }
}
