package com.akumakeito.commonui.presentation.navigation

import com.akumakeito.commonres.R


sealed class NavigationItem(
    val titleResId: Int,
    val screenRoute: ScreenRoute,
    val iconResId: Int
) {

    object PairRates : NavigationItem(
        titleResId = R.string.currency_rates,
        screenRoute = PairsScreenRoute,
        iconResId = R.drawable.ic_currency_rates
    )

    object Convert : NavigationItem(
        titleResId = R.string.converter,
        screenRoute = CurrencyConverterScreenRoute,
        iconResId = R.drawable.ic_converter
    )

    object Settings : NavigationItem(
        titleResId = R.string.settings,
        screenRoute = ParametersScreenRoute,
        iconResId = R.drawable.ic_settings
    )
}