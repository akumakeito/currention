package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akumakeito.commonres.R
import com.akumakeito.commonui.presentation.Dimens
import com.akumakeito.commonui.presentation.navigation.ScreenRoute
import com.akumakeito.parameters.presentation.SettingsItem
import com.akumakeito.parameters.presentation.SettingsViewModel
import ru.akumakeito.currention.ui.items.CustomTopAppBar

@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    onChangeFavoriteCurrencyClickListener: () -> Unit,
    aboutAppClickListener: () -> Unit,
) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        topBar = {
            CustomTopAppBar(
                currentScreen = ScreenRoute.SettingsScreenRoute
            )
        }
    ) { innerPadding ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Dimens.x2),
            contentAlignment = Alignment.BottomStart
        ) {

            Column {
                SettingsItem(
                    painter = painterResource(id = R.drawable.ic_switch_theme),
                    content = stringResource(id = R.string.dark_theme),
                    hasSwitch = true,
                    onItemClick = { viewModel.updateDarkTheme() },
                    swiched = state.isDarkTheme
                )

                SettingsItem(
                    painter = painterResource(id = R.drawable.ic_settings),
                    content = stringResource(id = R.string.system_theme),
                    hasSwitch = true,
                    onItemClick = { viewModel.updateSystemTheme() },
                    swiched = state.isSystemTheme
                )

                SettingsItem(
                    painter = painterResource(id = R.drawable.ic_change_fave_cur),
                    content = stringResource(id = R.string.change_fav_cur),
                    hasSwitch = false,
                    onItemClick = onChangeFavoriteCurrencyClickListener
                )

                val context = LocalContext.current

                SettingsItem(
                    painter = painterResource(id = R.drawable.ic_change_language),
                    content = stringResource(id = R.string.change_language),
                    hasSwitch = false,
                    onItemClick = { viewModel.changeLanguage(context) }
                )

                SettingsItem(
                    painter = painterResource(id = R.drawable.ic_info),
                    content = stringResource(id = R.string.app_info),
                    hasSwitch = false,
                    onItemClick = aboutAppClickListener
                )
            }
        }
    }
}