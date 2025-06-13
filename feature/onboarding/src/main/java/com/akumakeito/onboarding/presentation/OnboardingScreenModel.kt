package com.akumakeito.onboarding.presentation

import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.convert.presentation.search.SearchState

data class OnboardingScreenModel(
    val fiatCurrencyList: List<FiatCurrency>,
    val searchState: SearchState,
    val isButtonEnable : Boolean,
)