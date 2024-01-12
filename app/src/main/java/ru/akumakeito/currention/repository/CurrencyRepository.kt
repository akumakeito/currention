package ru.akumakeito.currention.repository

import ru.akumakeito.currention.dto.FiatCurrency

interface CurrencyRepository {
    suspend fun updateFlagFromJson()
    suspend fun updateFlagFromJson(jsonString: String)
    suspend fun getLatest()

    suspend fun getFiatCurrencyList() : List<FiatCurrency>
    suspend fun deleteAllFiat()
}

