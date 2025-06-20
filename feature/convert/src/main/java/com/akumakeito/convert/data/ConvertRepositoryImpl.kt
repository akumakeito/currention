package com.akumakeito.convert.data

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.convert.data.network.CurrencyApi
import com.akumakeito.convert.data.network.dto.ConvertFiatServerResponse
import com.akumakeito.convert.domain.ConvertRepository
import com.akumakeito.core.appsettings.AppSettingsRepository
import com.akumakeito.db.dao.CurrencyDao
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import java.io.IOException
import kotlin.time.Duration.Companion.hours
import kotlin.time.DurationUnit
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class ConvertRepositoryImpl(
    private val currencyApi: CurrencyApi,
    private val appSettingsRepository: AppSettingsRepository,
    private val currencyDao: CurrencyDao,
) : ConvertRepository {

    private val updatePeriod = 1.hours.toLong(DurationUnit.MILLISECONDS)


    override suspend fun convert(
        currencyFrom: FiatCurrency,
        currencyTo: FiatCurrency,
        amount: Double
    ): Result<ConvertFiatServerResponse> {
        return try {
            val result = currencyApi.convert(
                currencyFrom.shortCode,
                currencyTo.shortCode,
                amount
            )
            Result.success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.failure(e)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getCurrencyRatesOnPreviousDate(
        currencyFrom: FiatCurrency,
    ): Result<Map<String, Double>> {
        val requestDate =
            Clock.System.todayIn(TimeZone.currentSystemDefault()).minus(DatePeriod(days = 1))
        return try {
            val symbolsString = getFavoritesShortCodesString()
            val result = currencyApi.getCurrencyRateOnDate(
                currencyFrom.shortCode,
                requestDate.toString(),
                symbolsString
            ).rates
            Result.success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.failure(e)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getLatest(
        base: FiatCurrency,
    ): Result<Map<String, Double>> {

        val lastUpdate = appSettingsRepository.getLastRatesUpdate(base.shortCode).first()
        val time = Clock.System.now().toEpochMilliseconds()
        val shouldUpdate = time - lastUpdate > updatePeriod

        return try {
            val rates = if (shouldUpdate) {
                updateRates(
                    base,
                    time
                ).getOrThrow()
            } else {
                currencyDao.getCurrencyByShortCode(base.shortCode).rates ?: updateRates(
                    base,
                    time
                ).getOrThrow()
            }

            Result.success(rates)
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.failure(e)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun updateRates(
        base: FiatCurrency,
        time: Long?,
    ): Result<Map<String, Double>> {
        return try {
            val currentTime = time ?: Clock.System.now().toEpochMilliseconds()
            val symbolsString = getFavoritesShortCodesString()

            val result = currencyApi.getLatest(base.shortCode, symbolsString.toString()).rates
            currencyDao.updateCurrencyRates(base.shortCode, result.toString())

            appSettingsRepository.setLastRatesUpdate(base.shortCode, currentTime)

            Result.success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.failure(e)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    private suspend fun getFavoritesShortCodesString(): String {
        val favorites = currencyDao.getFavoriteCurrencies()
        return StringBuilder().apply {
            favorites.forEach {
                append(it.shortCode)
                append(",")
            }
        }.toString()
    }
}