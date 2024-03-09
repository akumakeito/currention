package ru.akumakeito.currention.domain

data class PairCurrency(
    val id : Int,
    val fromShortCode : String,
    val toShortCode : String,
    val toCurrencyLastRate : Double,
    val toCurrencyNewRate : Double,
    var differenceInPercentage : Double
) {
    fun getDifferenceInPercentage() : Double = (toCurrencyNewRate - toCurrencyLastRate) / toCurrencyLastRate * 100

}
