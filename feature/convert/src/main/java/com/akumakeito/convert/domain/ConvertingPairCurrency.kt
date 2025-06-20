package com.akumakeito.convert.domain

import com.akumakeito.core.models.domain.FiatCurrency

data class ConvertingPairCurrency(
    val firstCurrency: FiatCurrency,
    val secondCurrency: FiatCurrency,
    val rateFromFirstToSecond: Double,
    val rateFromSecondToFirst: Double,
    val amount: Double? = null,
    val convertedAmount: Double
)