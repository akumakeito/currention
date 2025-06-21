package com.akumakeito.convert.presentation.convert

import com.akumakeito.core.models.ErrorType
import com.akumakeito.core.models.domain.FiatCurrency

data class FavCurrencyConvertScreenModel(
    val from: FiatCurrency,
    val favCurrency: List<FiatCurrency>,
    val amount: String,
    val convertedToFavorites: List<FiatCurrency>,
    val isButtonEnable: Boolean = false,
    val isLoading: Boolean = true,
    val error: ErrorType? = null,
    val lastUpdate: String = "",
)