package com.akumakeito.rates.domain

import com.akumakeito.commonmodels.domain.FiatCurrency
import kotlinx.coroutines.flow.StateFlow

interface PairCurrencyRepository {
    val currencyPairs: StateFlow<List<PairCurrency>>
    suspend fun updateCurrencyPair(pairCurrency: PairCurrency)

    suspend fun updateCurrencyPairWithNewCurrencyFromOrTo(pairCurrency: PairCurrency): PairCurrency

    suspend fun isFromToCurrenciesSame(
        pairCurrency: PairCurrency,
        updatedPairCurrency: PairCurrency
    ): Boolean

    suspend fun getPairById(id: Int): PairCurrency
    suspend fun deletePairById(id: Int)
    suspend fun addNewCurrencyPair(pairCurrency: PairCurrency): PairCurrency
}