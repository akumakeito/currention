package ru.akumakeito.currention.dto

import ru.akumakeito.currention.R

data class FiatCurrency (
    val id : Int,
    val name : String,
    val shortCode: String,
    val code : String,
    val symbol : String,
    var flag : Int = 0
)
