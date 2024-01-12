package ru.akumakeito.currention.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import ru.akumakeito.currention.R
import ru.akumakeito.currention.api.ApiService
import ru.akumakeito.currention.dao.CurrencyDao
import ru.akumakeito.currention.dto.CurrencyType
import ru.akumakeito.currention.dto.FiatCurrency
import ru.akumakeito.currention.entity.toDto
import ru.akumakeito.currention.entity.toEntity
import ru.akumakeito.currention.util.FlagDeserializer
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: CurrencyDao,
    private val flagDeserializer: FlagDeserializer,
    @ApplicationContext private val context: Context
) : CurrencyRepository {
    override suspend fun updateFlagFromJson() {
        val res = context.resources
        val inputStream = res.openRawResource(R.raw.flags)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        updateFlagFromJson(jsonString)
    }

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

override suspend fun getFiatCurrencyList(): List<FiatCurrency> {
    if (dao.isEmpty()) {
        try {
            val result = apiService.getCurrencyList(CurrencyType.FIAT.name.lowercase())
            dao.insertAllFiat(result.response.toEntity())
            updateFlagFromJson()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return dao.getAllFiat().toDto()
}

override suspend fun deleteAllFiat() {
    dao.deleteAllFiat()
}
}