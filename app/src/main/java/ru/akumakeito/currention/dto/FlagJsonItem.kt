package ru.akumakeito.currention.dto

import com.google.gson.annotations.SerializedName

data class FlagJsonItem(
    @SerializedName("short_code")
    val shortCode: String,
    @SerializedName("flag")
    val flag: String
)
