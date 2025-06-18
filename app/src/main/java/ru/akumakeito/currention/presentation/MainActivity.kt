package ru.akumakeito.currention.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akumakeito.commonui.presentation.ErrorScreen
import com.akumakeito.commonui.presentation.LaunchState
import com.akumakeito.commonui.presentation.StartingScreen
import com.akumakeito.commonui.presentation.navigation.AboutAppScreenRoute
import com.akumakeito.commonui.presentation.navigation.AppNavGraph
import com.akumakeito.commonui.presentation.navigation.CurrencyConverterScreenRoute
import com.akumakeito.commonui.presentation.navigation.ErrorScreenRoute
import com.akumakeito.commonui.presentation.navigation.NavigationItem
import com.akumakeito.commonui.presentation.navigation.PairsScreenRoute
import com.akumakeito.commonui.presentation.navigation.ParametersScreenRoute
import com.akumakeito.commonui.presentation.navigation.Screen
import com.akumakeito.commonui.presentation.navigation.SelectFavoriteCurrencyScreenRoute
import com.akumakeito.commonui.presentation.navigation.SettingsScreenRoute
import com.akumakeito.commonui.presentation.navigation.StartingScreenRoute
import com.akumakeito.commonui.presentation.navigation.rememberNavigationState
import com.akumakeito.convert.presentation.convert.CurrencyConverterScreen
import com.akumakeito.parameters.presentation.AboutAppScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.currention.ui.screens.PairsScreen
import ru.akumakeito.currention.ui.screens.SelectFavoriteCurrencyScreen
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
            val isDarkSystem = isSystemInDarkTheme()

            val isDark by mainViewModel.isDark.collectAsStateWithLifecycle(isDarkSystem)

            val darkState by rememberSaveable {
                mutableStateOf(isDark)
            }
            val navigationState = rememberNavigationState()
            val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val startDestination = when (launchState) {
                LaunchState.Main -> PairsScreenRoute
                LaunchState.OnBoarding -> SelectFavoriteCurrencyScreenRoute(fromScreen = Screen.STARTING)
                LaunchState.Starting -> StartingScreenRoute
                LaunchState.Error -> ErrorScreenRoute
            }

            val showBarScreens = listOf(
                PairsScreenRoute,
                CurrencyConverterScreenRoute,
                SettingsScreenRoute,
            )
            var showBottomBar by remember { mutableStateOf(true) }

            LaunchedEffect(currentDestination) {
                showBottomBar = showBarScreens.any {
                    currentDestination?.hasRoute(
                        it::class
                    ) == true
                }
            }

            CurrentionTheme(
                useDarkTheme = darkState,
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            if (showBottomBar) {
                                NavigationBar(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    val navItems = listOf(
                                        NavigationItem.PairRates,
                                        NavigationItem.Convert,
                                        NavigationItem.Settings
                                    )

                                    navItems.forEach { item ->
                                        val selected =
                                            currentDestination?.hierarchy?.any { it.hasRoute(item.screenRoute::class) } == true

                                        NavigationBarItem(
                                            selected = selected,
                                            onClick = {
                                                if (!selected) {
                                                    navigationState.navigateTo(item.screenRoute)
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
                                PairsScreen(
                                    paddingValues = paddingValues,
                                    modifier = Modifier
                                )
                            },
                            convertScreenContent = {
                                CurrencyConverterScreen(
                                    paddingValues = paddingValues,
                                )
                            },
                            settingsListContent = {
                                SettingsScreen(
                                    paddingValues = paddingValues,
                                    onChangeFavoriteCurrencyClickListener = {
                                        navigationState.navigateTo(SelectFavoriteCurrencyScreenRoute())
                                    },
                                    aboutAppClickListener = {
                                        navigationState.navigateTo(AboutAppScreenRoute)
                                    }
                                )
                            },
                            selectFavCurrencyScreenContent = {
                                SelectFavoriteCurrencyScreen(
                                    modifier = Modifier,
                                    onDoneClick = {
                                        navigationState.navigateTo(ParametersScreenRoute)
                                    }
                                )
                            },
                            startingScreenContent = {
                                StartingScreen()
                            },
                            errorScreenContent = {
                                ErrorScreen()
                            },
                            aboutAppContent = {
                                AboutAppScreen()
                            }
                        )
                    }
                }
            }
        }
    }
}