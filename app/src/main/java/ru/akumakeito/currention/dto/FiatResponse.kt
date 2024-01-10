package ru.akumakeito.currention.dto

import androidx.room.ColumnInfo

data class FiatResponse(
    val id : Int,
    val name : String,
    val shortCode: String,
    val code : String,
    val precision : Int,
    val subunit : Int,
    val symbol : String,
    val symbol_first : Boolean,
    val decimal_mark : String,
    val thousands_separator : String
) : ResponseInterface
