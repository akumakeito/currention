package com.akumakeito.commonui.presentation.navigation

import com.akumakeito.commonres.R


sealed class NavigationItem(
    val titleResId: Int,
    val screenRoute: ScreenRoute,
    val iconResId: Int
) {

    object PairRates : NavigationItem(
        titleResId = R.string.currency_rates,
        screenRoute = ScreenRoute.PairScreenRoute,
        iconResId = R.drawable.ic_currency_rates
    )

    object Convert : NavigationItem(
        titleResId = R.string.converter,
        screenRoute = ScreenRoute.ConvertScreenRoute,
        iconResId = R.drawable.ic_converter
    )

    object Settings : NavigationItem(
        titleResId = R.string.settings,
        screenRoute = ScreenRoute.SettingsScreenRoute,
        iconResId = R.drawable.ic_settings
    )
}