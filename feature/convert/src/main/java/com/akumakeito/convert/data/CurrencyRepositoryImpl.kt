package com.akumakeito.convert.data

import android.content.Context
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.akumakeito.commonmodels.domain.CurrencyType
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonmodels.popularCurrencyShortCodeList
import com.akumakeito.convert.data.network.CurrencyApi
import com.akumakeito.convert.data.network.toEntity
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.db.dao.CurrencyDao
import com.akumakeito.db.entity.toModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class CurrencyRepositoryImpl(
    private val currencyApi: CurrencyApi,
    private val currencyDao: CurrencyDao,
    private val flagInteractor: FlagInteractor,
    @ApplicationContext private val context: Context,
) : CurrencyRepository {

    override val fiatCurrencies: Flow<List<FiatCurrency>> = currencyDao.getAllFiat().map {
        it.toModel()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun downloadInitialFiatCurrencyList(): Flow<Result<Unit>> = flow {
        if (currencyDao.isEmpty()) {
            try {
                val result = currencyApi.getCurrencyList(CurrencyType.FIAT.name.lowercase())

                currencyDao.insertAllFiat(result.response.toEntity())

                flagInteractor.updateFlagFromJson(context)

                popularCurrencyShortCodeList.map {
                    currencyDao.updateCurrencyPopularityByShortCode(it)
                }

                val res = context.resources
                fiatCurrencies.map { list ->
                    list.map {
                        val stringId = "cur${it.shortCode.lowercase()}"
                        val currencyNameId =
                            res.getIdentifier(stringId, "string", context.packageName)
                        currencyDao.updateCurrencyName(it.shortCode, res.getString(currencyNameId))
                    }
                }
                emit(Result.success(Unit))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.failure<Unit>(e))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.failure<Unit>(e))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.failure<Unit>(e))
            }
        } else {
            emit(Result.success(Unit))
        }
    }

    override suspend fun deleteAllFiat() {
        currencyDao.deleteAllFiat()
    }

    override suspend fun updateFavoriteCurrency(fiatCurrency: FiatCurrency) {
        val updated = fiatCurrency.copy(isFavorite = !fiatCurrency.isFavorite).toEntity()
        currencyDao.updateFavoriteCurrency(updated)
    }

    override suspend fun getPopularCurrencyList(): List<FiatCurrency> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteCurrencyList(): List<FiatCurrency> {
        return currencyDao.getFavoriteCurrencies().toModel()
    }

    override fun getFavoriteCurrencyListFlow(): Flow<List<FiatCurrency>> =
        currencyDao.getFavoriteCurrenciesFlow().map {
            it.toModel()
        }
}