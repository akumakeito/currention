package com.akumakeito.convert.data.network.dto

data class ConvertFiatResponse(
    val timestamp: Long,
    val date: String,
    val from: String,
    val to: String,
    val amount: Double,
    val value: Double
)
