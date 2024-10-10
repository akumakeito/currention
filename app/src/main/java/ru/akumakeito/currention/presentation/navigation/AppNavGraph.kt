package ru.akumakeito.currention.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    currencyRatesContent: @Composable () -> Unit,
    convertScreenContent: @Composable () -> Unit,
    settingsListContent: @Composable () -> Unit,
    changeFavCurrencyScreenContent: @Composable () -> Unit

) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.CurrencyRatesScreen.route
    ) {

        navigation(
            startDestination = Screen.SettingsListScreen.route,
            route = Screen.SettingsScreen.route
        ) {
            settingsScreenNavGraph(
                settingsListContent = settingsListContent,
                changeFavCurrencyScreenContent = changeFavCurrencyScreenContent
            )

        }
        composable(Screen.CurrencyRatesScreen.route) {
            currencyRatesContent()
        }

        composable(Screen.ConvertScreen.route) {
            convertScreenContent()
        }

//        composable(Screen.SettingsScreen.route) {
//            settingsListContent()
//        }


    }
}
