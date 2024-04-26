package ru.akumakeito.currention.dto

import ru.akumakeito.currention.domain.FiatCurrency

data class ConvertingPairCurrency(
    val firstCurrency: FiatCurrency,
    val secondCurrency: FiatCurrency,
    val rateFromFirstToSecond: Double,
    val rateFromSecondToFirst: Double,
    val amount: Double,
    val rateByAmount: Double
) {

//    val rateByAmount.setValue(formatRateByAmount())
    fun formatRateByAmount() : Double {
        return "%.2f".format(rateByAmount).toDouble()
    }
}