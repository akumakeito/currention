package com.akumakeito.onboarding.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.convert.presentation.search.SearchState
import com.akumakeito.convert.presentation.search.SearchingInteractor
import com.akumakeito.convert.presentation.search.doesMatchSearchQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val searchingInteractor: SearchingInteractor,
) : ViewModel() {

    init {
        getFiatCurrencies()
    }

    val searchingState = searchingInteractor.searchingState

    val fiatCurrencies = searchingInteractor.fiatCurrencies

    fun getFiatCurrencies() = viewModelScope.launch {
        repository.getInitialFiatCurrencyList()
    }

    fun onSearchTextChange(text: String) {
        searchingInteractor.onSearchTextChange(text)
    }


    fun updateFavoriteCurrency(currency: FiatCurrency) {
        Log.d("checkbox", "vm $currency")
        viewModelScope.launch {
            repository.updateFavoriteCurrency(currency)
        }
    }


}