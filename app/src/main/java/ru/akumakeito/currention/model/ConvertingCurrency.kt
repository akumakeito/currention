package ru.akumakeito.currention.model

import ru.akumakeito.currention.domain.FiatCurrency

data class ConvertingCurrency(
    val fromCurrency : FiatCurrency,
    val toCurrency : FiatCurrency,
    val amount : Int,
    val rate : Double
)
