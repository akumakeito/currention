package com.akumakeito.convert.data.network.dto

import com.google.gson.annotations.SerializedName

data class FiatResponse(
    val id: Int,
    val name: String,
    @SerializedName("short_code")
    val shortCode: String,
    val code: String,
    val precision: Int,
    val subunit: Int,
    val symbol: String,
    val symbol_first: Boolean,
    val decimal_mark: String,
    val thousands_separator: String
)
