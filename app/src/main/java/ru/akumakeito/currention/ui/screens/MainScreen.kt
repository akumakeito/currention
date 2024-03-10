package ru.akumakeito.currention.ui.screens

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.akumakeito.currention.navigation.AppNavGraph
import ru.akumakeito.currention.navigation.NavigationItem
import ru.akumakeito.currention.navigation.Screen

@Composable
fun MainScreen() {
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()

                val items = listOf(
                    NavigationItem.CurrencyRates,
                    NavigationItem.Convert,
                    NavigationItem.Settings
                )

                items.forEach { item ->
                    val selected = navBackStackEntry?.destination?.route == item.screen.route

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navHostController.navigate(item.screen.route) {
                                    popUpTo(Screen.CurrencyRatesScreen.route)
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.iconResId),
                                contentDescription = item.titleResId.toString()
                            )
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        }

                    )
                }
            }
        }
    ) { paddingValues ->

        AppNavGraph(
            navHostController = navHostController,
            currencyRatesContent = {
                CurrencyExchangeRatesScreen(paddingValues = paddingValues)
            },
            convertScreenContent = {
                CurrencyConverterScreen()
            },
            settingsScreenContent = {
                SettingsScreen()
            }
        )

    }

}



