package ru.akumakeito.currention.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.akumakeito.currention.domain.FiatCurrency

interface CurrencyRepository {

    val fiatCurrencies: Flow<List<FiatCurrency>>

    suspend fun updateFlagFromJson()
    suspend fun updateFlagFromJson(jsonString: String)
    suspend fun getLatest()

    suspend fun getFiatCurrencyList()
    suspend fun deleteAllFiat()

    suspend fun updateFavoriteCurrency(fiatCurrency: FiatCurrency)

    suspend fun updateCurrencyName(fiatCurrency: FiatCurrency)


    suspend fun setPopularCurrencyList(popularCurrencyShortCodeList: List<String>)

    suspend fun getPopularCurrencyList(): List<FiatCurrency>
}

