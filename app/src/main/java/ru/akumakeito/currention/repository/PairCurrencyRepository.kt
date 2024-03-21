package ru.akumakeito.currention.repository

import kotlinx.coroutines.flow.StateFlow
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency

interface PairCurrencyRepository {
    val currencyPairs : StateFlow<List<PairCurrency>>
    suspend fun updateCurrencyPair(pairCurrency: PairCurrency)

    suspend fun getPairRates(currencyFromShortCode : FiatCurrency, currencyToShortCode : FiatCurrency, amount : Int)

    suspend fun getPairById(id : Int) : PairCurrency
    suspend fun deletePairById(id : Int)
    suspend fun addNewCurrencyPair(pairCurrency: PairCurrency) : PairCurrency
}