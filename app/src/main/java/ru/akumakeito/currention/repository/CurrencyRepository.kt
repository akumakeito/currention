package ru.akumakeito.currention.repository

import ru.akumakeito.currention.dto.FiatCurrency

interface CurrencyRepository {
    suspend fun getLatest()

    suspend fun getFiatCurrencyList() : List<FiatCurrency>
}

