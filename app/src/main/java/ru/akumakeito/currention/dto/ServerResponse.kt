package ru.akumakeito.currention.dto

data class ServerResponse(
    val meta : Meta,
    val response : List<FiatResponse>
)

interface ResponseInterface {

}

data class Meta(
    val code : Int,
    val disclaimer : String
)
