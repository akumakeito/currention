package com.akumakeito.rates.data

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.akumakeito.convert.domain.ConvertRepository
import com.akumakeito.db.dao.CurrencyPairDao
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
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class PairCurrencyRepositoryImpl @Inject constructor(
    private val pairCurrencyDao: CurrencyPairDao,
    private val convertRepository: ConvertRepository,
) : PairCurrencyRepository {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override val currencyPairs: StateFlow<List<PairCurrency>> = pairCurrencyDao.getAllPairs().map {
        it.toModel()
    }.stateIn(scope, SharingStarted.Lazily, emptyList())

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun updateCurrencyPair(pairCurrency: PairCurrency): Result<Unit> {
        return try {
            val newRates =
                convertRepository.getLatest(
                    base = pairCurrency.fromCurrency,
                ).getOrThrow()[pairCurrency.toCurrency.shortCode]

            val previousRate = convertRepository.getCurrencyRatesOnPreviousDate(
                pairCurrency.fromCurrency,
            ).getOrThrow()[pairCurrency.toCurrency.shortCode]

            val updatedPair = pairCurrency.copy(
                toCurrencyLastRate = previousRate!!,
                toCurrencyNewRate = newRates!!
            )

            val updatedPairWithRate =
                updatedPair.copy(rateCurrency = updatedPair.getRateInPerc())

            pairCurrencyDao.updateCurrencyPair(updatedPairWithRate.toEntity())

            Result.success(Unit)
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure<Unit>(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.failure<Unit>(e)
        }
        catch (e: Exception) {
            e.printStackTrace()
            Result.failure<Unit>(e)
        }
    }

    override suspend fun updateCurrencyPairWithNewCurrencyFromOrTo(pairCurrency: PairCurrency): PairCurrency {
        val updatedPair = pairCurrency.copy(
            toCurrencyLastRate = 0.0
        )
        pairCurrencyDao.updateCurrencyPair(updatedPair.toEntity())
        return updatedPair
    }

    override suspend fun getPairById(id: Int): PairCurrency? {
        val pair = pairCurrencyDao.getPairByShortCode(id)
        return pair?.toModel()
    }

    override suspend fun deletePairById(id: Int) {
        pairCurrencyDao.deletePairById(id)
    }
}