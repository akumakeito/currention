package com.akumakeito.rates.data

import android.util.Log
import com.akumakeito.convert.data.network.CurrencyApi
import com.akumakeito.convert.domain.ConvertRepository
import com.akumakeito.db.dao.CurrencyPairDao
import com.akumakeito.db.entity.PairCurrencyEntity
import com.akumakeito.rates.domain.PairCurrency
import com.akumakeito.rates.domain.PairCurrencyRepository
import com.akumakeito.rates.domain.toEntity
import com.akumakeito.rates.domain.toModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class PairCurrencyRepositoryImpl @Inject constructor(
    private val pairCurrencyDao: CurrencyPairDao,
    private val convertRepository: ConvertRepository,
) : PairCurrencyRepository {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override val currencyPairs: StateFlow<List<PairCurrency>> = pairCurrencyDao.getAllPairs().map {
        it.toModel()
    }.stateIn(scope, SharingStarted.Lazily, emptyList())

    override suspend fun updateCurrencyPair(pairCurrency: PairCurrency) {

        var oldPair = getPairById(pairCurrency.id)

        val newRates = convertRepository.convert(pairCurrency.fromCurrency, pairCurrency.toCurrency, 1.0)

        if (isFromToCurrenciesSame(pairCurrency, oldPair)) {
            oldPair = updateCurrencyPairWithNewCurrencyFromOrTo(pairCurrency)
        }

        var updatedPair =
            if (oldPair.toCurrencyLastRate == 0.0 || pairCurrency.toCurrencyLastRate == 0.0) {
                val previousRate = convertRepository.getCurrencyRateOnPreviousDate(
                    pairCurrency.fromCurrency,
                    pairCurrency.toCurrency
                ).rates[pairCurrency.toCurrency.shortCode]

                Log.d("PairCurrencyRepositoryImpl", "previousRate: $previousRate")
                pairCurrency.copy(
                    toCurrencyLastRate = previousRate ?: 0.0,
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
        pairCurrencyDao.updateCurrencyPair(updatedPairWithRate.toEntity())
    }

    override suspend fun updateCurrencyPairWithNewCurrencyFromOrTo(pairCurrency: PairCurrency): PairCurrency {
        val updatedPair = pairCurrency.copy(
            toCurrencyLastRate = 0.0
        )
        pairCurrencyDao.updateCurrencyPair(updatedPair.toEntity())
        return updatedPair
    }


    override suspend fun isFromToCurrenciesSame(
        pairCurrency: PairCurrency,
        updatedPairCurrency: PairCurrency
    ): Boolean =
        updatedPairCurrency.fromCurrency.shortCode.lowercase() != pairCurrency.fromCurrency.shortCode.lowercase()
                || updatedPairCurrency.toCurrency.shortCode.lowercase() != pairCurrency.toCurrency.shortCode.lowercase()


    override suspend fun getPairById(id: Int): PairCurrency {
        val pair = pairCurrencyDao.getPairById(id)
        return pair.toModel()
    }

    override suspend fun deletePairById(id: Int) {
        pairCurrencyDao.deletePairById(id)
    }

    override suspend fun addNewCurrencyPair(pairCurrency: PairCurrency): PairCurrency {
        pairCurrencyDao.addNewCurrencyPair(pairCurrency.toEntity())
        val newPair = pairCurrencyDao.getLastInsertedPair()
        return newPair.toModel()
    }
}