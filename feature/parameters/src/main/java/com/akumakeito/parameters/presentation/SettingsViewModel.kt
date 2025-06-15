package com.akumakeito.parameters.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akumakeito.core.appsettings.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        SettingsScreenModel()
    )

    val state = _state.asStateFlow()

    init {
        combine(
            appSettingsRepository.isDarkTheme,
            appSettingsRepository.isSystemTheme
        ) { isDarkTheme, isSystemTheme ->
            Pair(isDarkTheme, isSystemTheme)
        }.onEach { (isDarkTheme, isSystemTheme) ->
            _state.value = _state.value.copy(
                isDarkTheme = isDarkTheme,
                isSystemTheme = isSystemTheme
            )
        }.launchIn(viewModelScope)
    }

    fun updateDarkTheme() = viewModelScope.launch {
        if (_state.value.isSystemTheme) {
            appSettingsRepository.setSystemTheme(false)
        }
        appSettingsRepository.setDarkTheme(!_state.value.isDarkTheme)
    }

    fun updateSystemTheme() = viewModelScope.launch {
        if (_state.value.isDarkTheme) {
            appSettingsRepository.setDarkTheme(false)
        }
        appSettingsRepository.setSystemTheme(!_state.value.isSystemTheme)
    }

    fun changeLanguage(context: Context) = viewModelScope.launch {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val intent = Intent(
                Settings.ACTION_APP_LOCALE_SETTINGS,
                Uri.parse("package:${context.packageName}")
            )
            context.startActivity(intent)
        } else {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            context.startActivity(intent)
        }

    }
}