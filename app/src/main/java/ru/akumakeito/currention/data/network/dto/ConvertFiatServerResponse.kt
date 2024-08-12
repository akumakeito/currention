package ru.akumakeito.currention.data.network.dto

data class ConvertFiatServerResponse(
    val meta: Meta,
    val response: ConvertFiatResponse,
    val timestamp: Long,
    val date: String,
    val from: String,
    val to: String,
    val amount: Double,
    val value: Double
)
