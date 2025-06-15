package com.akumakeito.convert.presentation.search

import com.akumakeito.commonmodels.domain.FiatCurrency

fun FiatCurrency.doesMatchSearchQuery(query: String): Boolean {

    val machingCombinations = listOf(
        nameRu,
        "$nameRu $shortCode",
        "$nameRu$shortCode",
        shortCode,
        "$shortCode $nameRu",
        "$shortCode$nameRu",
        nameEn,
        "$nameEn $shortCode",
        "$nameEn$shortCode",
        shortCode,
        "$shortCode $nameEn",
        "$shortCode$nameEn"
    )

    return machingCombinations.any { it.contains(query, ignoreCase = true) }
}