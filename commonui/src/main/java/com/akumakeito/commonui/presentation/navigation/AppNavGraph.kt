package com.akumakeito.commonui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    startDestination: String,
    pairScreenContent: @Composable () -> Unit,
    convertScreenContent: @Composable () -> Unit,
    settingsListContent: @Composable () -> Unit,
    changeFavCurrencyScreenContent: @Composable () -> Unit,
    startingScreenContent: @Composable () -> Unit,
    errorScreenContent: @Composable () -> Unit
) {

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
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

        composable(ScreenRoute.ChangeFavoriteCurrencyScreenRoute.route) {
            changeFavCurrencyScreenContent()
        }

        composable(ScreenRoute.PairScreenRoute.route) {
            pairScreenContent()
        }

        composable(ScreenRoute.ConvertScreenRoute.route) {
            convertScreenContent()
        }

        composable(ScreenRoute.SettingsScreenRoute.route) {
            settingsListContent()
        }

        composable(ScreenRoute.StartingScreenRoute.route) {
            startingScreenContent()
        }
        composable(ScreenRoute.ErrorScreenRoute.route) {
            errorScreenContent()
        }
    }
}
