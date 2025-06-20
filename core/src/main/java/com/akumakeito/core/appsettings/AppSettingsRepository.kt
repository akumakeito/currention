package com.akumakeito.core.appsettings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private const val FIRST_START_KEY = "first_start"
        private const val LAST_SELECTED_BASE_CURRENCY = "last_selected_base_currency"
        private const val DARK_THEME = "dark_theme"
        private const val SYSTEM_THEME = "system_theme"
        private const val LAST_RATES_UPDATE = "last_rates_update"
    }

    val isDarkTheme: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(DARK_THEME)] ?: false
        }

    val isSystemTheme: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(SYSTEM_THEME)] ?: true
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

    suspend fun setLastSelectedBaseCurrency(shortCode: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(LAST_SELECTED_BASE_CURRENCY)] = shortCode
        }
    }

    suspend fun getLastSelectedBaseCurrency(): String {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(LAST_SELECTED_BASE_CURRENCY)] ?: ""
        }.first()
    }

    suspend fun setDarkTheme(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(DARK_THEME)] = value
        }
    }

    suspend fun getDarkTheme(): Boolean? {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(DARK_THEME)]
        }.first()
    }

    suspend fun setSystemTheme(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(SYSTEM_THEME)] = value
        }
    }

    suspend fun getSystemTheme(): Boolean? {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(SYSTEM_THEME)]
        }.first()
    }

    suspend fun setLastRatesUpdate(shortCode: String,timeMillis : Long) {
        dataStore.edit {prefs ->
            prefs[longPreferencesKey("${shortCode}_$LAST_RATES_UPDATE")] = timeMillis
        }
    }

    fun getLastRatesUpdate(shortCode: String) : Flow<Long> {
        return dataStore.data.map { prefs ->
            prefs[longPreferencesKey("${shortCode}_$LAST_RATES_UPDATE")] ?: 0L
        }
    }
}