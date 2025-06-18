package com.akumakeito.commonui.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    startDestination: ScreenRoute,
    pairScreenContent: @Composable () -> Unit,
    convertScreenContent: @Composable () -> Unit,
    settingsListContent: @Composable () -> Unit,
    selectFavCurrencyScreenContent: @Composable () -> Unit,
    startingScreenContent: @Composable () -> Unit,
    errorScreenContent: @Composable () -> Unit,
    aboutAppContent: @Composable () -> Unit,
) {
    NavHost(
        navHostController, startDestination = startDestination,
    ) {
        composable<SelectFavoriteCurrencyScreenRoute> {
            selectFavCurrencyScreenContent()
        }
        composable<PairsScreenRoute> {
            pairScreenContent()
        }
        composable<CurrencyConverterScreenRoute> {
            convertScreenContent()
        }

        composable<StartingScreenRoute> {
            startingScreenContent()
        }
        composable<ErrorScreenRoute> {
            errorScreenContent()
        }

        navigation<ParametersScreenRoute>(
            startDestination = SettingsScreenRoute,
        ) {
            composable<SettingsScreenRoute> {
                settingsListContent()
            }

            composable<SelectFavoriteCurrencyScreenRoute> {
                selectFavCurrencyScreenContent()
            }

            composable<AboutAppScreenRoute> {
                aboutAppContent()
            }
        }
    }
}
