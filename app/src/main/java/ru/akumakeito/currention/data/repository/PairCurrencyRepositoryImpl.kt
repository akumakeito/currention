package ru.akumakeito.currention.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.akumakeito.currention.data.db.dao.CurrencyPairDao
import ru.akumakeito.currention.data.db.entity.PairCurrencyEntity
import ru.akumakeito.currention.data.db.entity.toDto
import ru.akumakeito.currention.data.network.ApiService
import ru.akumakeito.currention.data.network.dto.ConvertFiatOnDateServerResponse
import ru.akumakeito.currention.domain.model.FiatCurrency
import ru.akumakeito.currention.domain.model.PairCurrency
import ru.akumakeito.currention.domain.repository.PairCurrencyRepository
import java.time.LocalDate
import javax.inject.Inject

class PairCurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val pairCurrencyDao: CurrencyPairDao,
) : PairCurrencyRepository {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override val currencyPairs: StateFlow<List<PairCurrency>> = pairCurrencyDao.getAllPairs().map {
        it.toDto()
    }.stateIn(scope, SharingStarted.Lazily, emptyList())

    override suspend fun updateCurrencyPair(pairCurrency: PairCurrency) {

        var oldPair = getPairById(pairCurrency.id)

        val newRates = convert(pairCurrency.fromCurrency, pairCurrency.toCurrency, 1.0)

        if (isFromToCurrenciesSame(pairCurrency, oldPair)) {
            oldPair = updateCurrencyPairWithNewCurrencyFromOrTo(pairCurrency)
        }


        var updatedPair =
            if (oldPair.toCurrencyLastRate == 0.0 || pairCurrency.toCurrencyLastRate == 0.0) {
                val previousRate = getCurrencyRateOnPreviousDate(
                    pairCurrency.fromCurrency,
                    pairCurrency.toCurrency
                ).rates[pairCurrency.toCurrency.shortCode]

                Log.d("PairCurrencyRepositoryImpl", "previousRate: $previousRate")
                pairCurrency.copy(
                    toCurrencyLastRate = previousRate ?: 0.1,
                    toCurrencyNewRate = newRates.value
                )
            } else if (newRates.value == pairCurrency.toCurrencyNewRate) {
                pairCurrency
            } else {
                pairCurrency.copy(
                    toCurrencyLastRate = pairCurrency.toCurrencyNewRate,
                    toCurrencyNewRate = newRates.value
                )
            }

        val updatedPairWithRate = updatedPair.copy(rateCurrency = updatedPair.getRateInPerc())
        pairCurrencyDao.updateCurrencyPair(PairCurrencyEntity.fromDto(updatedPairWithRate))

    }

    override suspend fun updateCurrencyPairWithNewCurrencyFromOrTo(pairCurrency: PairCurrency): PairCurrency {
        val updatedPair = pairCurrency.copy(
            toCurrencyLastRate = 0.0
        )
        pairCurrencyDao.updateCurrencyPair(PairCurrencyEntity.fromDto(updatedPair))
        return updatedPair
    }


    override suspend fun convert(
        currencyFrom: FiatCurrency,
        currencyTo: FiatCurrency,
        amount: Double
    ) = apiService.getPairRates(
        currencyFrom.shortCode,
        currencyTo.shortCode,
        amount
    )

    override suspend fun isFromToCurrenciesSame(
        pairCurrency: PairCurrency,
        updatedPairCurrency: PairCurrency
    ): Boolean =
        updatedPairCurrency.fromCurrency.shortCode.lowercase() != pairCurrency.fromCurrency.shortCode.lowercase()
                || updatedPairCurrency.toCurrency.shortCode.lowercase() != pairCurrency.toCurrency.shortCode.lowercase()


    override suspend fun getCurrencyRateOnPreviousDate(
        currencyFrom: FiatCurrency,
        currencyTo: FiatCurrency
    ): ConvertFiatOnDateServerResponse {
        val requestDate = LocalDate.now().minusDays(1)
        return apiService.getCurrencyRateOnDate(
            currencyFrom.shortCode,
            requestDate.toString(),
            listOf(currencyTo.shortCode)
        )
    }


    override suspend fun getPairById(id: Int): PairCurrency {
        val pair = pairCurrencyDao.getPairById(id)
        return pair.toDto()
    }

    override suspend fun deletePairById(id: Int) {
        pairCurrencyDao.deletePairById(id)
    }

    override suspend fun addNewCurrencyPair(pairCurrency: PairCurrency): PairCurrency {
        pairCurrencyDao.addNewCurrencyPair(PairCurrencyEntity.fromDto(pairCurrency))
        val newPair = pairCurrencyDao.getLastInsertedPair()
        return newPair.toDto()
    }

}