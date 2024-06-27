package ru.akumakeito.currention.data.network.dto

import ru.akumakeito.currention.domain.model.FiatCurrency

data class ConvertingPairCurrency(
    val firstCurrency: FiatCurrency,
    val secondCurrency: FiatCurrency,
    val rateFromFirstToSecond: Double,
    val rateFromSecondToFirst: Double,
    val amount: Double,
    val rateByAmount: Double
) {


}