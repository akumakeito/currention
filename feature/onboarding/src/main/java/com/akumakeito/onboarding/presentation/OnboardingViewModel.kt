package com.akumakeito.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.convert.presentation.search.SearchState
import com.akumakeito.convert.presentation.search.SearchingInteractor
import com.akumakeito.core.appsettings.AppSettingsRepository
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
class OnboardingViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val appSettingsRepository: AppSettingsRepository,
    private val searchingInteractor: SearchingInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow(
        OnboardingScreenModel(
            fiatCurrencyList = emptyList(),
            searchState = SearchState(),
            isButtonEnable = false
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

    fun onDoneClick() = viewModelScope.launch {
        appSettingsRepository.setFirstStart(false)
    }
}