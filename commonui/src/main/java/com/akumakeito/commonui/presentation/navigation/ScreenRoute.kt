package com.akumakeito.commonui.presentation.navigation

import kotlinx.serialization.Serializable

interface ScreenRoute

@Serializable
data class SelectFavoriteCurrencyScreenRoute(val fromScreen : Screen = Screen.SETTINGS) : ScreenRoute

@Serializable
data object StartingScreenRoute : ScreenRoute

@Serializable
data object ErrorScreenRoute : ScreenRoute

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