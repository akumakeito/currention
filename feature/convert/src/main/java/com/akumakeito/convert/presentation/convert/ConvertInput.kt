package com.akumakeito.convert.presentation.convert

import com.akumakeito.core.models.domain.FiatCurrency

internal data class ConvertInput(
    val favList : List<FiatCurrency>,
    val base : String,
    val lastUpdate : Long,
    val amount : String,
)
