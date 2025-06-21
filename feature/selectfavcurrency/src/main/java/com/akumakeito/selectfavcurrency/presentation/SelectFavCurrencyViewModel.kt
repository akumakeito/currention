package com.akumakeito.selectfavcurrency.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.akumakeito.core.models.domain.FiatCurrency
import com.akumakeito.commonui.presentation.navigation.SelectFavoriteCurrencyScreenRoute
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.convert.presentation.search.SearchState
import com.akumakeito.convert.presentation.search.SearchingInteractor
import com.akumakeito.core.util.log
import com.akumakeito.selectfavcurrency.domain.SaveFavoriteCurrencyListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectFavCurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val searchingInteractor: SearchingInteractor,
    private val saveFavoriteCurrencyListUseCase: SaveFavoriteCurrencyListUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val route = savedStateHandle.toRoute<SelectFavoriteCurrencyScreenRoute>()

    private val _state = MutableStateFlow(
        SelectFavCurrencyScreenModel(
            fiatCurrencyList = emptyList(),
            searchState = SearchState(),
            isButtonEnable = false,
            screenFrom = route.fromScreen
        )
    )

    val state = _state.asStateFlow()

    init {
        combine(
            searchingInteractor.fiatCurrencies,
            searchingInteractor.searchingState,
            repository.getFavoriteCurrencyListFlow()
        ) { fiatCurrencies, searchState, favoriteCurrencies ->
            Triple(fiatCurrencies, searchState, favoriteCurrencies)
        }.distinctUntilChanged()
            .onEach { (fiatCurrencies, searchState, favoriteCurrencies) ->
                _state.update {
                    it.copy(
                        fiatCurrencyList = fiatCurrencies,
                        searchState = searchState,
                        isButtonEnable = favoriteCurrencies.isNotEmpty()
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun onSearchTextChange(text: String) {
        searchingInteractor.onSearchTextChange(text)
    }

    fun updateFavoriteCurrency(currency: FiatCurrency) {
        viewModelScope.launch {
            repository.updateFavoriteCurrency(currency)
        }
    }

    fun onDoneClick() {
        log("onDoneClick")
        saveFavoriteCurrencyListUseCase.runAsync()
    }
}