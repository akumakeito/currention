package ru.akumakeito.currention.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akumakeito.commonui.presentation.LaunchState
import com.akumakeito.commonui.presentation.StartingScreen
import com.akumakeito.commonui.presentation.items.KeyboardAware
import com.akumakeito.commonui.presentation.navigation.AppNavGraph
import com.akumakeito.commonui.presentation.navigation.NavigationItem
import com.akumakeito.commonui.presentation.navigation.ScreenRoute
import com.akumakeito.commonui.presentation.navigation.ScreenRoute.ChangeFavoriteCurrencyScreenRoute
import com.akumakeito.commonui.presentation.navigation.rememberNavigationState
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.currention.ui.screens.ChooseFavoriteCurrencyScreen
import ru.akumakeito.currention.ui.screens.CurrencyConverterScreen
import ru.akumakeito.currention.ui.screens.PairsScreen
import ru.akumakeito.currention.ui.screens.SettingsScreen
import ru.akumakeito.currention.ui.theme.CurrentionTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val launchState by mainViewModel.launchState.collectAsStateWithLifecycle(LaunchState.Starting)

            val navigationState = rememberNavigationState()
            val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
            val currentScreenRoute = navBackStackEntry?.destination?.route

            val startDestination = when (launchState) {
                LaunchState.Main -> ScreenRoute.PairScreenRoute.route
                LaunchState.OnBoarding -> ScreenRoute.ChangeFavoriteCurrencyScreenRoute.route
                LaunchState.Starting -> ScreenRoute.StartingScreenRoute.route
            }

            val hideBars = listOf(
                ChangeFavoriteCurrencyScreenRoute.route,
                ScreenRoute.StartingScreenRoute.route
            )

            CurrentionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            if (!hideBars.contains(currentScreenRoute)) {
                                NavigationBar {
                                    val navItems = listOf(
                                        NavigationItem.PairRates,
                                        NavigationItem.Convert,
                                        NavigationItem.Settings
                                    )

                                    navItems.forEach { item ->
                                        val selected =
                                            navBackStackEntry?.destination?.route == item.screenRoute.route

                                        NavigationBarItem(
                                            selected = selected,
                                            onClick = {
                                                if (!selected) {
                                                    navigationState.navigateTo(item.screenRoute.route)
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
                        }
                    ) { paddingValues ->

                        AppNavGraph(
                            navHostController = navigationState.navHostController,
                            startDestination = startDestination,
                            pairScreenContent = {
                                KeyboardAware {
                                    PairsScreen(
                                        paddingValues = paddingValues,
                                        modifier = Modifier
                                    )
                                }
                            },
                            convertScreenContent = {
                                CurrencyConverterScreen(
                                    paddingValues = paddingValues,
                                )
                            },
                            settingsListContent = {
                                SettingsScreen(
                                    paddingValues = paddingValues,
                                    onSwitchTheme = {
                                    },
                                    onChangeFavoriteCurrencyClickListener = {
                                        navigationState.navigateTo(ScreenRoute.ChangeFavoriteCurrencyScreenRoute.route)
                                    }
                                )
                            },
                            changeFavCurrencyScreenContent = {
                                ChooseFavoriteCurrencyScreen(
                                    onDoneClick = {
                                        navigationState.navigateTo(ScreenRoute.PairScreenRoute.route)
                                    }
                                )
                            },
                            startingScreenContent = {
                                StartingScreen()
                            }
                        )
                    }
                }
            }
        }
    }
}