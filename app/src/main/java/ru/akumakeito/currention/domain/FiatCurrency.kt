package ru.akumakeito.currention.domain

import ru.akumakeito.currention.R

data class FiatCurrency (
    val id : Int,
    val name : String,
    val shortCode: String,
    val code : String,
    val symbol : String,
    var flag : Int = 0,
    var isPopular : Boolean = false,
    var isFavorite : Boolean = false
)
