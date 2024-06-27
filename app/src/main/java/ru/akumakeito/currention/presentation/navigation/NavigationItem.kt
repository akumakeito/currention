package ru.akumakeito.currention.presentation.navigation

import ru.akumakeito.currention.R

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val iconResId: Int
) {

    object CurrencyRates : NavigationItem(
        screen = Screen.CurrencyRatesScreen,
        titleResId = R.string.currency_rates,
        iconResId = R.drawable.ic_currency_rates
    )

    object Convert : NavigationItem(
        screen = Screen.ConvertScreen,
        titleResId = R.string.converter,
        iconResId = R.drawable.ic_converter
    )

    object Settings : NavigationItem(
        screen = Screen.SettingsScreen,
        titleResId = R.string.settings,
        iconResId = R.drawable.ic_settings
    )
}