package ru.akumakeito.currention.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.settingsScreenNavGraph(
    settingsListContent: @Composable () -> Unit,
    changeFavCurrencyScreenContent: @Composable () -> Unit

) {
    navigation(
        startDestination = Screen.SettingsListScreen.route,
        route = Screen.SettingsScreen.route
    ) {
        composable(Screen.ChangeFavoriteCurrencyScreen.route) {
            changeFavCurrencyScreenContent()
        }

        composable(Screen.SettingsListScreen.route) {
            settingsListContent()
        }
    }

}