package com.akumakeito.rates.domain

import com.akumakeito.db.entity.PairCurrencyEntity

fun PairCurrencyEntity.toModel() = PairCurrency(
        id,
        fromCurrency,
        toCurrency,
        toCurrencyLastRate,
        toCurrencyNewRate,
        rateCurrency
    )

    fun PairCurrency.toEntity() = PairCurrencyEntity(
        id,
        fromCurrency,
        toCurrency,
        toCurrencyLastRate,
        toCurrencyNewRate,
        rateCurrency
    )

fun List<PairCurrencyEntity>.toModel(): List<PairCurrency> = map { it.toModel() }

