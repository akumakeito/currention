package com.akumakeito.convert.domain

import com.akumakeito.commonmodels.domain.FiatCurrency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    val fiatCurrencies: Flow<List<FiatCurrency>>

    suspend fun getLatest()

    suspend fun getInitialFiatCurrencyList()
    suspend fun deleteAllFiat()

    suspend fun updateFavoriteCurrency(fiatCurrency: FiatCurrency)

    suspend fun getPopularCurrencyList(): List<FiatCurrency>
}