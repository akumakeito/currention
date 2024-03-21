package ru.akumakeito.currention.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency

interface CurrencyRepository {

    val fiatCurrencies : Flow<List<FiatCurrency>>
    val currencyPairs : StateFlow<List<PairCurrency>>
    suspend fun updateFlagFromJson()
    suspend fun updateFlagFromJson(jsonString: String)
    suspend fun getLatest()

    suspend fun getFiatCurrencyList()
    suspend fun deleteAllFiat()

    suspend fun updateFavoriteCurrency(fiatCurrency: FiatCurrency)

    suspend fun updateCurrencyName(fiatCurrency: FiatCurrency)

    suspend fun updateCurrencyPair(pairCurrency: PairCurrency)

    suspend fun getPairRates(currencyFromShortCode : FiatCurrency, currencyToShortCode : FiatCurrency, amount : Int)

    suspend fun getPairById(id : Int) : PairCurrency
    suspend fun deletePairById(id : Int)
    suspend fun addNewCurrencyPair(pairCurrency: PairCurrency) : PairCurrency
    suspend fun setPopularCurrencyList(popularCurrencyShortCodeList : List<String>)

    suspend fun getPopularCurrencyList() : List<FiatCurrency>
}

