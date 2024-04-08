package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import ru.akumakeito.currention.R
import ru.akumakeito.currention.navigation.AppNavGraph
import ru.akumakeito.currention.navigation.NavigationItem
import ru.akumakeito.currention.navigation.Screen
import ru.akumakeito.currention.navigation.rememberNavigationState
import ru.akumakeito.currention.viewmodel.CurrencyViewModel
import ru.akumakeito.currention.viewmodel.PairCurrencyViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    currencyViewModel: CurrencyViewModel = hiltViewModel(),
    pairViewModel: PairCurrencyViewModel = hiltViewModel()
) {
    val navigationState = rememberNavigationState()
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val currentScreenRoute = navBackStackEntry?.destination?.route


    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val isEditing by pairViewModel.isEditing.collectAsState()


    LaunchedEffect(key1 = keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }



    Scaffold(
        floatingActionButton = {
            if (currentScreenRoute == Screen.CurrencyRatesScreen.route) {
                if(!isEditing) {
                    FloatingActionButton(
                        modifier = Modifier
                            .padding(16.dp),
                        onClick = {
                            pairViewModel.addNewCurrencyPair()
                        },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {

                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = stringResource(R.string.add_new_pair)
                        )

                    }

                }
            }
        },

        topBar = {
            TopAppBar(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                title = {
                    Text(
                        text = stringResource(
                            id = Screen.getScreenByRoute(
                                currentScreenRoute ?: ""
                            ).titleResId
                        ),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                actions = {
                    if (currentScreenRoute == Screen.CurrencyRatesScreen.route) {
                        IconButton(onClick = { pairViewModel.updateAllPairsRates()}) {
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
            Column {

            if (isEditing) {
                Button(
                    onClick = { pairViewModel.updatePair() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                ) {
                    Text(
                        text = stringResource(R.string.done),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

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
        }
    ) { paddingValues ->

        AppNavGraph(
            navHostController = navigationState.navHostController,
            currencyRatesContent = {
                KeyboardAware {
                    CurrencyExchangeRatesScreen(paddingValues = paddingValues, pairViewModel = pairViewModel, modifier = Modifier.verticalScroll(scrollState)

                    )
                }
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



