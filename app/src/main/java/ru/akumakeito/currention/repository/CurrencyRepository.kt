package ru.akumakeito.currention.repository

import kotlinx.coroutines.flow.Flow
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency
import ru.akumakeito.currention.entity.FiatEntity

interface CurrencyRepository {

    val fiatCurrencies : Flow<List<FiatCurrency>>
    val currencyPairs : Flow<List<PairCurrency>>
    suspend fun updateFlagFromJson()
    suspend fun updateFlagFromJson(jsonString: String)
    suspend fun getLatest()

    suspend fun getFiatCurrencyList()
    suspend fun deleteAllFiat()

    suspend fun updateFavoriteCurrency(fiatCurrency: FiatCurrency)

    suspend fun updateCurrencyName(fiatCurrency: FiatCurrency)

    suspend fun getPairRates(currencyFromShortCode : FiatCurrency, currencyToShortCode : FiatCurrency, amount : Int)

    suspend fun addNewCurrencyPair(pairCurrency: PairCurrency)
    suspend fun setPopularCurrencyList(popularCurrencyShortCodeList : List<String>)

    suspend fun getPopularCurrencyList() : List<FiatCurrency>
}

