package com.akumakeito.rates.domain

import com.akumakeito.commonmodels.domain.FiatCurrency

data class PairCurrency(
    val id: Int,
    val fromCurrency: FiatCurrency,
    val toCurrency: FiatCurrency,
    val toCurrencyLastRate: Double,
    val toCurrencyNewRate: Double,
    val rateCurrency: Float?
) {
    fun getRateInPerc(): Float =
        ((toCurrencyNewRate - toCurrencyLastRate) / toCurrencyLastRate * 100).toFloat()

}