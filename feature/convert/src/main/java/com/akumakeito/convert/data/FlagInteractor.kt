package com.akumakeito.convert.data

import android.content.Context
import com.akumakeito.commonres.R
import com.akumakeito.db.dao.CurrencyDao
import javax.inject.Inject

class FlagInteractor @Inject constructor(
    private val flagDeserializer: FlagDeserializer,
    private val currencyDao: CurrencyDao
) {

    suspend fun updateFlagFromJson(context: Context,) {
        val res = context.resources
        val inputStream = res.openRawResource(R.raw.flags)
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        flagDeserializer.deserialize(jsonString).forEach { flagJson ->
            val flagId = res.getIdentifier(flagJson.flag, "drawable", context.packageName)
            currencyDao.updateFlagByShortCode(flagJson.shortCode, flagId)
        }
    }
}