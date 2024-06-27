package ru.akumakeito.currention.data.network.dto

data class FiatServerResponse(
    val meta: Meta,
    val response: List<FiatResponse>
)


