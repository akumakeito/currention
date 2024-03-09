package ru.akumakeito.currention.dto

data class FiatServerResponse(
    val meta : Meta,
    val response : List<FiatResponse>
)


