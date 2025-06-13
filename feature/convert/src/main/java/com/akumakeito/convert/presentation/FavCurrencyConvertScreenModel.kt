package com.akumakeito.convert.presentation

import com.akumakeito.commonmodels.domain.FiatCurrency

data class FavCurrencyConvertScreenModel(
    val from : FiatCurrency,
    val favCurrency : List<FiatCurrency>,
    val amount : String,
    val convertedToFavorites : List<FiatCurrency>,
    val isButtonEnable : Boolean = false,
    val isLoading : Boolean = true,
)
