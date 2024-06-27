package ru.akumakeito.currention.presentation.navigation

import ru.akumakeito.currention.R

sealed class Screen(
    val route: String,
    val titleResId: Int
) {
    object CurrencyRatesScreen : Screen(CURRENCY_RATES_ROUTE, R.string.currency_rates)
    object ConvertScreen : Screen(CONVERT_ROUTE, R.string.converter)
    object SettingsScreen : Screen(SETTINGS_ROUTE, R.string.settings)

    companion object {
        const val CURRENCY_RATES_ROUTE = "currency_rates"
        const val CONVERT_ROUTE = "convert"
        const val SETTINGS_ROUTE = "settings"

        fun getScreenByRoute(route: String): Screen {
            return when (route) {
                CURRENCY_RATES_ROUTE -> CurrencyRatesScreen
                CONVERT_ROUTE -> ConvertScreen
                SETTINGS_ROUTE -> SettingsScreen
                else -> CurrencyRatesScreen
            }
        }
    }
}