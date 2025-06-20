package com.akumakeito.rates.domain

import kotlinx.coroutines.flow.StateFlow

interface PairCurrencyRepository {
    val currencyPairs: StateFlow<List<PairCurrency>>
    suspend fun updateCurrencyPair(pairCurrency: PairCurrency) : Result<Unit>

    suspend fun updateCurrencyPairWithNewCurrencyFromOrTo(pairCurrency: PairCurrency): PairCurrency

    suspend fun getPairById(id: Int): PairCurrency?
    suspend fun deletePairById(id: Int)
}