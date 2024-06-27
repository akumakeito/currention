package ru.akumakeito.currention.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.akumakeito.currention.data.network.dto.ConvertFiatOnDateServerResponse
import ru.akumakeito.currention.data.network.dto.ConvertFiatServerResponse
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency

interface PairCurrencyRepository {
    val currencyPairs: StateFlow<List<PairCurrency>>
    suspend fun updateCurrencyPair(pairCurrency: PairCurrency)

    suspend fun updateCurrencyPairWithNewCurrencyFromOrTo(pairCurrency: PairCurrency): PairCurrency

    suspend fun convert(
        currencyFrom: FiatCurrency,
        currencyTo: FiatCurrency,
        amount: Double,
    ): ConvertFiatServerResponse

    suspend fun isFromToCurrenciesSame(
        pairCurrency: PairCurrency,
        updatedPairCurrency: PairCurrency
    ): Boolean

    suspend fun getCurrencyRateOnPreviousDate(
        currencyFrom: FiatCurrency,
        currencyTo: FiatCurrency
    ): ConvertFiatOnDateServerResponse

    suspend fun getPairById(id: Int): PairCurrency
    suspend fun deletePairById(id: Int)
    suspend fun addNewCurrencyPair(pairCurrency: PairCurrency): PairCurrency
}