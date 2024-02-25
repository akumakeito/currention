package ru.akumakeito.currention.domain

data class FiatServerResponse(
    val meta : Meta,
    val response : List<FiatResponse>
)


