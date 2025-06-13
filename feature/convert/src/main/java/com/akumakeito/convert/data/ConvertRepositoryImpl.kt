package com.akumakeito.convert.data

import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.convert.data.network.CurrencyApi
import com.akumakeito.convert.data.network.dto.ConvertFiatOnDateServerResponse
import com.akumakeito.convert.domain.ConvertRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn

class ConvertRepositoryImpl(private val currencyApi: CurrencyApi) : ConvertRepository {

    override suspend fun convert(
        currencyFrom: FiatCurrency,
        currencyTo: FiatCurrency,
        amount: Double
    ) = currencyApi.getPairRates(
        currencyFrom.shortCode,
        currencyTo.shortCode,
        amount
    )

    override suspend fun getCurrencyRateOnPreviousDate(
        currencyFrom: FiatCurrency,
        currencyTo: FiatCurrency
    ): ConvertFiatOnDateServerResponse {
        val requestDate =
            Clock.System.todayIn(TimeZone.currentSystemDefault()).minus(DatePeriod(days = 1))
        return currencyApi.getCurrencyRateOnDate(
            currencyFrom.shortCode,
            requestDate.toString(),
            listOf(currencyTo.shortCode)
        )
    }

    override suspend fun getLatest(
        base: FiatCurrency,
        symbols: List<FiatCurrency>
    ): ConvertFiatOnDateServerResponse {
        val symbolsList = symbols.map { it.shortCode }
        return currencyApi.getLatest(base.shortCode)
    }
}