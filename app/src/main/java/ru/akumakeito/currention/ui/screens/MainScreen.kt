package ru.akumakeito.currention.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.akumakeito.currention.CurrencyViewModel
import ru.akumakeito.currention.R
import ru.akumakeito.currention.navigation.AppNavGraph
import ru.akumakeito.currention.navigation.NavigationItem
import ru.akumakeito.currention.navigation.NavigationState
import ru.akumakeito.currention.navigation.Screen
import ru.akumakeito.currention.navigation.rememberNavigationState
import ru.akumakeito.currention.ui.items.CustomTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    currencyViewModel: CurrencyViewModel = hiltViewModel()
) {
    val navigationState = rememberNavigationState()
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val currentScreenRoute = navBackStackEntry?.destination?.route


    Scaffold(
        floatingActionButton = {
            if ( currentScreenRoute == Screen.CurrencyRatesScreen.route) {
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {

                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.add_new_pair)
                    )

                }
            }

        },

        topBar = {
            TopAppBar(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                title = {
                    Text(
                        text = stringResource(id = Screen.getScreenByRoute(currentScreenRoute ?: "").titleResId),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                actions = {
                    if (currentScreenRoute == Screen.CurrencyRatesScreen.route) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_update_rates),
                                contentDescription = stringResource(
                                    R.string.update_rates
                                ),
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            )
        },

        bottomBar = {
            NavigationBar {


                val navItems = listOf(
                    NavigationItem.CurrencyRates,
                    NavigationItem.Convert,
                    NavigationItem.Settings
                )

                navItems.forEach { item ->
                    val selected = navBackStackEntry?.destination?.route == item.screen.route

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
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
            navHostController = navigationState.navHostController,
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



