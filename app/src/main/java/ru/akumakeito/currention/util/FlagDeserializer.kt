package ru.akumakeito.currention.util

import com.google.gson.Gson
import ru.akumakeito.currention.dto.FiatCurrency
import ru.akumakeito.currention.dto.FlagJsonItem
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FlagDeserializer  @Inject constructor(
    private val gson: Gson
) {
    fun deserialize(jsonString: String): List<FlagJsonItem> {
        val flagType = object : com.google.gson.reflect.TypeToken<List<FlagJsonItem>>() {}.type
        return gson.fromJson(jsonString, flagType)
    }
}