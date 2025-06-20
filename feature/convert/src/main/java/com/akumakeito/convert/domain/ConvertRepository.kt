package com.akumakeito.convert.domain

import com.akumakeito.core.models.domain.FiatCurrency
import com.akumakeito.convert.data.network.dto.ConvertFiatServerResponse

interface ConvertRepository {

    suspend fun convert(
        currencyFrom: FiatCurrency,
        currencyTo: FiatCurrency,
        amount: Double
    ): Result<ConvertFiatServerResponse>

    suspend fun getCurrencyRatesOnPreviousDate(
        currencyFrom: FiatCurrency,
    ): Result<Map<String, Double>>

    suspend fun getLatest(base: FiatCurrency): Result<Map<String, Double>>

    suspend fun updateRates(
        base: FiatCurrency,
        time: Long? = null,
    ): Result<Map<String, Double>>
}