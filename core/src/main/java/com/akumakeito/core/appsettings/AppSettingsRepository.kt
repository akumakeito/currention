package com.akumakeito.core.appsettings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppSettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        const val FIRST_START_KEY = "first_start"
    }

    suspend fun setFirstStart(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(FIRST_START_KEY)] = value
        }
    }

    fun getFirstStart(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(FIRST_START_KEY)] ?: true
        }
    }
}