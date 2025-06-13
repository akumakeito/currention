package com.akumakeito.convert.data

import android.content.Context
import android.util.Log
import com.akumakeito.commonmodels.domain.CurrencyType
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonmodels.popularCurrencyShortCodeList
import com.akumakeito.commonui.presentation.LaunchState
import com.akumakeito.commonui.presentation.ResultState
import com.akumakeito.convert.data.network.CurrencyApi
import com.akumakeito.convert.data.network.toEntity
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.db.dao.CurrencyDao
import com.akumakeito.db.entity.toModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CurrencyRepositoryImpl(
    private val currencyApi: CurrencyApi,
    private val currencyDao: CurrencyDao,
    private val flagInteractor: FlagInteractor,
    @ApplicationContext private val context: Context,
) : CurrencyRepository {

    override val fiatCurrencies: Flow<List<FiatCurrency>> = currencyDao.getAllFiat().map {
        it.toModel()
    }

    override suspend fun getLatest() {

    }

    override fun downloadInitialFiatCurrencyList() :Flow<ResultState> = flow {
        emit(ResultState.Loading)
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
                        val currencyNameId = res.getIdentifier(stringId, "strings", context.packageName)
                        currencyDao.updateCurrencyName(it.shortCode, res.getString(currencyNameId))
                    }
                }
                emit(ResultState.Success)

            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
        emit(ResultState.Success)
    }

    override suspend fun deleteAllFiat() {
        currencyDao.deleteAllFiat()
    }

    override suspend fun updateFavoriteCurrency(fiatCurrency: FiatCurrency) {
        currencyDao.updateFavoriteCurrency(fiatCurrency.id)
    }

    override suspend fun getPopularCurrencyList(): List<FiatCurrency> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteCurrencyList(): List<FiatCurrency> {
        return currencyDao.getFavoriteCurrencies().toModel()
    }

    override fun getFavoriteCurrencyListFlow(): Flow<List<FiatCurrency>> = flow {
        val result = getFavoriteCurrencyList()
        emit(result)
    }
}