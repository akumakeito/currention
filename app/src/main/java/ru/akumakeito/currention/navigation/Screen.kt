package ru.akumakeito.currention.navigation

sealed class Screen(
    val route : String
) {
    object CurrencyRatesScreen : Screen(CURRENCY_RATES_ROUTE)
    object ConvertScreen : Screen(CONVERT_ROUTE)
    object SettingsScreen : Screen(SETTINGS_ROUTE)

    companion object {
        const val CURRENCY_RATES_ROUTE = "currency_rates"
        const val CONVERT_ROUTE = "convert"
        const val SETTINGS_ROUTE = "settings"
    }
}