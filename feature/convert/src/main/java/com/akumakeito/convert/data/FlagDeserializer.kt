package com.akumakeito.convert.data

import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlagDeserializer @Inject constructor(
    private val gson: Gson
) {
    fun deserialize(jsonString: String): List<FlagJsonItem> {
        val flagType = object : com.google.gson.reflect.TypeToken<List<FlagJsonItem>>() {}.type
        return gson.fromJson(jsonString, flagType)
    }
}