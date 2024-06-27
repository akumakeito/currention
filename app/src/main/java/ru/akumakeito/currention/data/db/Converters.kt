package ru.akumakeito.currention.data.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.akumakeito.currention.domain.model.FiatCurrency
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(val gson: Gson) {

    @TypeConverter
    fun fromPairToJson(pair: FiatCurrency): String {
        val type = object : TypeToken<FiatCurrency>() {}.type
        return gson.toJson(pair, type)
    }

    @TypeConverter
    fun fromJsonToPair(json: String): FiatCurrency {
        val type = object : TypeToken<FiatCurrency>() {}.type
        return gson.fromJson(json, type)
    }

}