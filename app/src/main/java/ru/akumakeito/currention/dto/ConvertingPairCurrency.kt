package ru.akumakeito.currention.dto

import ru.akumakeito.currention.domain.FiatCurrency

data class ConvertingPairCurrency(
    val firstCurrency : FiatCurrency,
    val secondCurrency : FiatCurrency,
    val rateFromFirstToSecond : Double,
    val rateFromSecondToFirst : Double
)
