package ru.akumakeito.currention.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    currencyRatesContent: @Composable () -> Unit,
    convertScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.CurrencyRatesScreen.route
    ) {
        composable(Screen.CurrencyRatesScreen.route) {
            currencyRatesContent()
        }

        composable(Screen.ConvertScreen.route) {
            convertScreenContent()
        }

        composable(Screen.SettingsScreen.route) {
            settingsScreenContent()
        }

    }
}
