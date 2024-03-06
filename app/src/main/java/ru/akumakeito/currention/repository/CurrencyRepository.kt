package ru.akumakeito.currention.repository

import kotlinx.coroutines.flow.Flow
import ru.akumakeito.currention.domain.FiatCurrency

interface CurrencyRepository {

    val fiatCurrencies : Flow<List<FiatCurrency>>
    suspend fun updateFlagFromJson()
    suspend fun updateFlagFromJson(jsonString: String)
    suspend fun getLatest()

    suspend fun getFiatCurrencyList()
    suspend fun deleteAllFiat()

    suspend fun chooseFavoriteCurrency(fiatCurrency: FiatCurrency)

    suspend fun markPopularCurrency()

    suspend fun getPopularCurrencyList() : List<FiatCurrency>
}

