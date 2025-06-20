package com.akumakeito.selectfavcurrency.presentation

import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonui.presentation.navigation.Screen
import com.akumakeito.convert.presentation.search.SearchState

data class SelectFavCurrencyScreenModel(
    val fiatCurrencyList: List<FiatCurrency>,
    val searchState: SearchState,
    val isButtonEnable : Boolean,
    val screenFrom : Screen,
)