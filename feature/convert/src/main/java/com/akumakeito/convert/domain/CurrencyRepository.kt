package com.akumakeito.convert.domain

import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonui.presentation.LaunchState
import com.akumakeito.commonui.presentation.ResultState
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    val fiatCurrencies: Flow<List<FiatCurrency>>

    suspend fun getLatest()

    fun downloadInitialFiatCurrencyList() : Flow<ResultState>
    suspend fun deleteAllFiat()

    suspend fun updateFavoriteCurrency(fiatCurrency: FiatCurrency)

    suspend fun getPopularCurrencyList(): List<FiatCurrency>
    suspend fun getFavoriteCurrencyList(): List<FiatCurrency>
}