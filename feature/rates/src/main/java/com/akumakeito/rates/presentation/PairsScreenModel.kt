package com.akumakeito.rates.presentation

import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonui.presentation.ErrorType
import com.akumakeito.rates.domain.PairCurrency

data class PairsScreenModel(
    val pairs: List<PairCurrency>,
    val favoriteCurrencies : List<FiatCurrency>,
    val editingPair : PairCurrency,
    val isEditing : Boolean,
    val isLoading : Boolean = false,
    val isError : ErrorType? = null
)