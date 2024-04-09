package ru.akumakeito.currention.dto

data class ConvertFiatOnDateResponse(
    val date: String,
    val base : String,
    val rates : Map<String, Double>
)