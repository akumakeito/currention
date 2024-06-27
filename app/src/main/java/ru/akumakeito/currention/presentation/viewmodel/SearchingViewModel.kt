package ru.akumakeito.currention.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import ru.akumakeito.currention.domain.repository.CurrencyRepository
import ru.akumakeito.currention.domain.state.SearchState
import javax.inject.Inject

@HiltViewModel
class SearchingViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _searchingState = MutableStateFlow(SearchState())
    val searchingState = _searchingState.asStateFlow()

    private val _fiatCurrencies = currencyRepository.fiatCurrencies

    @OptIn(ExperimentalCoroutinesApi::class)
    val fiatCurrencies = _searchingState.flatMapLatest { state ->
        _fiatCurrencies.map { currencies ->
            if (state.searchText.isBlank()) {
                currencies.filter { it.isPopular }  //TODO заменить на is Favorite
            } else {
                currencies.filter { it.doesMatchSearchQuery(state.searchText) }

            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchingState.update {
            it.copy(searchText = text)
        }
    }
}