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
) {
    fun doesMatchSearchQuery(query: String) :Boolean {
        val machingCombinations = listOf(
            "$name",
            "$name $shortCode",
            "$name$shortCode",
            "$shortCode",
            "$shortCode $name",
            "$shortCode$name"
        )

        return machingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
