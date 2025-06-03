package com.akumakeito.commonui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.settingsScreenNavGraph(
    settingsListContent: @Composable () -> Unit,
    changeFavCurrencyScreenContent: @Composable () -> Unit

) {
    navigation(
        startDestination = ScreenRoute.SettingsListScreenRoute.route,
        route = ScreenRoute.SettingsScreenRoute.route
    ) {
        composable(ScreenRoute.ChangeFavoriteCurrencyScreenRoute.route) {
            changeFavCurrencyScreenContent()
        }

        composable(ScreenRoute.SettingsListScreenRoute.route) {
            settingsListContent()
        }
    }

}