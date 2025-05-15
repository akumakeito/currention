package ru.akumakeito.currention.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    pairScreenContent: @Composable () -> Unit,
    convertScreenContent: @Composable () -> Unit,
    settingsListContent: @Composable () -> Unit,
    changeFavCurrencyScreenContent: @Composable () -> Unit

) {

    NavHost(
        navController = navHostController,
        startDestination = ScreenRoute.PairScreenRoute.route
    ) {

//        navigation(
//            startDestination = ScreenRoute.SettingsListScreenRoute.route,
//            route = ScreenRoute.SettingsScreenRoute.route
//        ) {
//            settingsScreenNavGraph(
//                settingsListContent = settingsListContent,
//                changeFavCurrencyScreenContent = changeFavCurrencyScreenContent
//            )
//
//        }
        composable(ScreenRoute.PairScreenRoute.route) {
            pairScreenContent()
        }

        composable(ScreenRoute.ConvertScreenRoute.route) {
            convertScreenContent()
        }

        composable(ScreenRoute.SettingsScreenRoute.route) {
            settingsListContent()
        }
    }
}
