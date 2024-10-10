package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.akumakeito.currention.R
import ru.akumakeito.currention.presentation.ui.items.SettingsItem

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onSwitchTheme: () -> Unit,
    onChangeFavoriteCurrencyClickListener: () -> Unit
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {

        Column {
            SettingsItem(
                painter = painterResource(id = R.drawable.ic_switch_theme),
                content = stringResource(id = R.string.switch_theme),
                hasSwitch = true,
                onSwitch = { /*TODO*/ },
                onItemClick = onSwitchTheme
            )

            SettingsItem(
                painter = painterResource(id = R.drawable.ic_change_fave_cur),
                content = stringResource(id = R.string.change_fav_cur),
                hasSwitch = false,
                onSwitch = { },
                onItemClick = onChangeFavoriteCurrencyClickListener
            )

            SettingsItem(
                painter = painterResource(id = R.drawable.ic_change_language),
                content = stringResource(id = R.string.change_language),
                hasSwitch = false,
                onSwitch = { },
                onItemClick = { }
            )

            SettingsItem(
                painter = painterResource(id = R.drawable.ic_info),
                content = stringResource(id = R.string.app_info),
                hasSwitch = false,
                onSwitch = { },
                onItemClick = { }
            )
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {

    SettingsScreen(
        onSwitchTheme = {},
        onChangeFavoriteCurrencyClickListener = {}
    )

}