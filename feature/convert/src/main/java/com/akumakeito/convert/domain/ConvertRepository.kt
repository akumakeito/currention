package com.akumakeito.convert.domain

import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.convert.data.network.dto.ConvertFiatOnDateServerResponse
import com.akumakeito.convert.data.network.dto.ConvertFiatServerResponse

interface ConvertRepository {

    suspend fun convert(
        currencyFrom: FiatCurrency,
        currencyTo: FiatCurrency,
        amount: Double
    ): ConvertFiatServerResponse

    suspend fun getCurrencyRateOnPreviousDate(
        currencyFrom: FiatCurrency,
        currencyTo: FiatCurrency
    ): ConvertFiatOnDateServerResponse
}