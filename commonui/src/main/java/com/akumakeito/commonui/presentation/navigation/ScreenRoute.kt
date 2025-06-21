package com.akumakeito.commonui.presentation.navigation

import com.akumakeito.core.models.ErrorType
import kotlinx.serialization.Serializable

interface ScreenRoute

@Serializable
data class SelectFavoriteCurrencyScreenRoute(val fromScreen : Screen = Screen.SETTINGS) : ScreenRoute

@Serializable
data object StartingScreenRoute : ScreenRoute

@Serializable
data class ErrorScreenRoute(val errorType : ErrorType = ErrorType.UNKNOWN) : ScreenRoute

@Serializable
data object CurrencyConverterScreenRoute : ScreenRoute

@Serializable
data object PairsScreenRoute : ScreenRoute

@Serializable
data object SettingsScreenRoute : ScreenRoute

@Serializable
data object ParametersScreenRoute : ScreenRoute

@Serializable
data object AboutAppScreenRoute : ScreenRoute

enum class Screen {
    SETTINGS,
    STARTING,
}