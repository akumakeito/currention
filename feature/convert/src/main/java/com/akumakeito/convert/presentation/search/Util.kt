package com.akumakeito.convert.presentation.search

import com.akumakeito.commonmodels.domain.FiatCurrency

fun FiatCurrency.doesMatchSearchQuery(query: String): Boolean {

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