package com.akumakeito.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akumakeito.core.models.domain.FiatCurrency

@Entity(tableName = "pair_currency")
data class PairCurrencyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @Embedded(prefix = "from_")
    val fromCurrency: FiatCurrency,
    @Embedded(prefix = "to_")
    val toCurrency: FiatCurrency,
    val toCurrencyLastRate: Double,
    val toCurrencyNewRate: Double,
    val rateCurrency: Float?
)