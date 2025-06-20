package com.akumakeito.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    @TypeConverter
    fun fromMap(map: Map<String, Double>): String = gson.toJson(map)

    @TypeConverter
    fun fromStringToMap(value: String?): Map<String, Double> {
        return if (value.isNullOrEmpty()) {
            emptyMap()
        } else {

            val type = object : TypeToken<Map<String, Double>>() {}.type
            return gson.fromJson(value, type)
        }
    }

}