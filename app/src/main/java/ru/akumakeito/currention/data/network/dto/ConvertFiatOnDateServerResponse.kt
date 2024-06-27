package ru.akumakeito.currention.data.network.dto

data class ConvertFiatOnDateServerResponse(
    val meta: Meta,
    val response: ConvertFiatOnDateResponse,
    val date: String,
    val base: String,
    val rates: Map<String, Double>
)