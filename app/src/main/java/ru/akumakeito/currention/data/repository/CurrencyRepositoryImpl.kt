package ru.akumakeito.currention.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.akumakeito.currention.R
import ru.akumakeito.currention.data.db.dao.CurrencyDao
import ru.akumakeito.currention.data.db.entity.toDto
import ru.akumakeito.currention.data.db.entity.toEntity
import ru.akumakeito.currention.data.network.ApiService
import ru.akumakeito.currention.domain.model.CurrencyType
import ru.akumakeito.currention.domain.model.FiatCurrency
import ru.akumakeito.currention.domain.repository.CurrencyRepository
import ru.akumakeito.currention.presentation.util.Constants.Companion.popularCurrencyShortCodeList
import ru.akumakeito.currention.presentation.util.FlagDeserializer
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val currencyDao: CurrencyDao,
    private val flagDeserializer: FlagDeserializer,
    @ApplicationContext private val context: Context,

    ) : CurrencyRepository {


    override val fiatCurrencies: Flow<List<FiatCurrency>> = currencyDao.getAllFiat().map {
        it.toDto()
    }.flowOn(Dispatchers.IO)


    override suspend fun updateFlagFromJson() {
        val res = context.resources
        val inputStream = res.openRawResource(R.raw.flags)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        updateFlagFromJson(jsonString)
    }

    @SuppressLint("DiscouragedApi")
    override suspend fun updateFlagFromJson(jsonString: String) {
        val flagList = flagDeserializer.deserialize(jsonString)
        val res = context.resources
        flagList.forEach { flagJson ->
            val flagId = res.getIdentifier(flagJson.flag, "drawable", context.packageName)

            currencyDao.updateFlagByShortCode(flagJson.shortCode, flagId)
        }
    }

    override suspend fun getLatest() {

    }

    override suspend fun getFiatCurrencyList() {
        if (currencyDao.isEmpty()) {
            try {
                val result = apiService.getCurrencyList(CurrencyType.FIAT.name.lowercase())
                currencyDao.insertAllFiat(result.response.toEntity())
                updateFlagFromJson()
                setPopularCurrencyList(popularCurrencyShortCodeList)
                fiatCurrencies.map { list ->
                    list.map {
                        updateCurrencyName(it)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun deleteAllFiat() {
        currencyDao.deleteAllFiat()
    }

    override suspend fun updateFavoriteCurrency(fiatCurrency: FiatCurrency) {
        Log.d("checkbox", "repo $fiatCurrency")
        currencyDao.updateFavoriteCurrency(fiatCurrency.id)
    }

    override suspend fun updateCurrencyName(fiatCurrency: FiatCurrency) {
        val res = context.resources
        val stringId = "cur${fiatCurrency.shortCode.lowercase()}"
        val currencyNameId = res.getIdentifier(stringId, "strings", context.packageName)
        currencyDao.updateCurrencyName(fiatCurrency.shortCode, res.getString(currencyNameId))

    }


    override suspend fun setPopularCurrencyList(popularCurrencyShortCodeList: List<String>) {
        popularCurrencyShortCodeList.map {
            currencyDao.updateCurrencyPopularityByShortCode(it)
        }
    }

    override suspend fun getPopularCurrencyList(): List<FiatCurrency> {
        TODO("Not yet implemented")
    }
}