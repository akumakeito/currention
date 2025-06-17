package com.akumakeito.convert.domain

import com.akumakeito.commonmodels.domain.FiatCurrency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    val fiatCurrencies: Flow<List<FiatCurrency>>

    fun downloadInitialFiatCurrencyList(): Flow<Result<Unit>>
    suspend fun deleteAllFiat()

    suspend fun updateFavoriteCurrency(fiatCurrency: FiatCurrency)

    suspend fun getPopularCurrencyList(): List<FiatCurrency>
    suspend fun getFavoriteCurrencyList(): List<FiatCurrency>
    fun getFavoriteCurrencyListFlow(): Flow<List<FiatCurrency>>
}