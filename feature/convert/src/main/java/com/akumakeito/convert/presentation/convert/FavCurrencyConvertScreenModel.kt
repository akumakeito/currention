package com.akumakeito.convert.presentation.convert

import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonui.presentation.ErrorType

data class FavCurrencyConvertScreenModel(
    val from : FiatCurrency,
    val favCurrency : List<FiatCurrency>,
    val amount : String,
    val convertedToFavorites : List<FiatCurrency>,
    val isButtonEnable : Boolean = false,
    val isLoading : Boolean = true,
    val isError : ErrorType? = null
)