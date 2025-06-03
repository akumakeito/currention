package com.akumakeito.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akumakeito.appsettings.AppSettingsRepository
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.convert.presentation.search.SearchingInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val appSettingsRepository: AppSettingsRepository,
    private val searchingInteractor: SearchingInteractor,
) : ViewModel() {

    init {
        getFiatCurrencies()
    }

    val searchingState = searchingInteractor.searchingState

    val fiatCurrencies = searchingInteractor.fiatCurrencies

    private val _selectedCurrencies = MutableStateFlow<List<FiatCurrency>>(emptyList())

    val isButtonEnable = _selectedCurrencies.map { it.isNotEmpty() }

    fun getFiatCurrencies() = viewModelScope.launch {
        repository.downloadInitialFiatCurrencyList()
    }

    fun onSearchTextChange(text: String) {
        searchingInteractor.onSearchTextChange(text)
    }

    fun updateFavoriteCurrency(currency: FiatCurrency) {
        viewModelScope.launch {
            _selectedCurrencies.update { list ->
                list.toMutableList().apply {
                    if (contains(currency)) {
                        remove(currency)
                    } else {
                        add(currency)
                    }
                }
            }
            repository.updateFavoriteCurrency(currency)
        }
    }

    fun onDoneClick() = viewModelScope.launch {
        appSettingsRepository.setFirstStart(false)
    }
}