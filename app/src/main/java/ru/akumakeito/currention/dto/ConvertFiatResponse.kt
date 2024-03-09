package ru.akumakeito.currention.dto

data class ConvertFiatResponse(
    val timestamp : Long,
    val date : String,
    val from : String,
    val to : String,
    val amount : Int,
    val value : Double
)
