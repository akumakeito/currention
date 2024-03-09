package ru.akumakeito.currention.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.akumakeito.currention.R
import ru.akumakeito.currention.api.ApiService
import ru.akumakeito.currention.dao.CurrencyDao
import ru.akumakeito.currention.domain.CurrencyType
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.entity.FiatEntity
import ru.akumakeito.currention.entity.toDto
import ru.akumakeito.currention.entity.toEntity
import ru.akumakeito.currention.util.Constants.Companion.popularCurrencyShortCodeList
import ru.akumakeito.currention.util.FlagDeserializer
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: CurrencyDao,
    private val flagDeserializer: FlagDeserializer,
    @ApplicationContext private val context: Context,

    ) : CurrencyRepository {

    override val fiatCurrencies: Flow<List<FiatCurrency>> = dao.getAllFiat().map {
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

            dao.updateFlagByShortCode(flagJson.shortCode, flagId)
        }
    }

    override suspend fun getLatest() {

    }

    override suspend fun getFiatCurrencyList() {
        if (dao.isEmpty()) {
            try {
                val result = apiService.getCurrencyList(CurrencyType.FIAT.name.lowercase())
                dao.insertAllFiat(result.response.toEntity())
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
        dao.deleteAllFiat()
    }

    override suspend fun updateFavoriteCurrency(fiatCurrency: FiatCurrency) {
        Log.d("checkbox", "repo $fiatCurrency")
        dao.updateFavoriteCurrency(fiatCurrency.id)
    }

    override suspend fun updateCurrencyName(fiatCurrency: FiatCurrency) {
        val resId = context.resources.getIdentifier(
            "cur${fiatCurrency.shortCode.lowercase()}",
            "string",
            context.packageName
        )
        val nameRus = context.getString(resId)
        dao.updateCurrencyName(fiatCurrency.shortCode, nameRus)

    }

    override suspend fun setPopularCurrencyList(popularCurrencyShortCodeList : List<String>) {
        popularCurrencyShortCodeList.map {
            dao.updateCurrencyPopularityByShortCode(it)
        }
    }

    override suspend fun getPopularCurrencyList(): List<FiatCurrency> {
        TODO("Not yet implemented")
    }
}