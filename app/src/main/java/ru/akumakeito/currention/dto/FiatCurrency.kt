package ru.akumakeito.currention.dto

data class FiatCurrency (
    val id : Int,
    val name : String,
    val shortCode: String? = null,
    val code : String,
    val symbol : String
)
