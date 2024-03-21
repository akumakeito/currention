package ru.akumakeito.currention.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.akumakeito.currention.api.ApiService
import ru.akumakeito.currention.dao.CurrencyPairDao
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency
import ru.akumakeito.currention.entity.PairCurrencyEntity
import ru.akumakeito.currention.entity.toDto
import javax.inject.Inject

class PairCurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val pairCurrencyDao: CurrencyPairDao,
    ) : PairCurrencyRepository {
    private val scope : CoroutineScope = CoroutineScope(Dispatchers.IO)

    override val currencyPairs: StateFlow<List<PairCurrency>> = pairCurrencyDao.getAllPairs().map {
        it.toDto()
    }.stateIn(scope, SharingStarted.Lazily, emptyList())

    override suspend fun updateCurrencyPair(pairCurrency: PairCurrency) {
        pairCurrencyDao.updateCurrencyPair(PairCurrencyEntity.fromDto(pairCurrency))
    }

    override suspend fun getPairRates(
        currencyFromShortCode: FiatCurrency,
        currencyToShortCode: FiatCurrency,
        amount: Int
    ) {
        apiService.getPairRates(
            currencyFromShortCode.shortCode,
            currencyToShortCode.shortCode,
            amount
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