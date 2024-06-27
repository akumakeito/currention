package ru.akumakeito.currention.data.network.dto

data class ConvertFiatOnDateResponse(
    val date: String,
    val base: String,
    val rates: Map<String, Double>
)