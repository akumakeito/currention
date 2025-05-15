package ru.akumakeito.currention.presentation.navigation

import com.akumakeito.commonres.R

sealed class ScreenRoute(
    val route: String,
    val titleResId: Int
) {
    object PairScreenRoute : ScreenRoute(PAIRS_ROUTE, R.string.currency_rates)
    object ConvertScreenRoute : ScreenRoute(CONVERT_ROUTE, R.string.converter)
    object SettingsScreenRoute : ScreenRoute(SETTINGS_ROUTE, R.string.settings)
    object SettingsListScreenRoute : ScreenRoute(SETTINGS_LIST_ROUTE, R.string.settings)
    object ChangeFavoriteCurrencyScreenRoute :
        ScreenRoute(CHANGE_FAVORITE_CURRENCY_ROUTE, R.string.favorite_currencies)


    companion object {
        const val PAIRS_ROUTE = "pairs"
        const val CONVERT_ROUTE = "convert"
        const val SETTINGS_ROUTE = "settings"
        const val SETTINGS_LIST_ROUTE = "settings_list"
        const val CHANGE_FAVORITE_CURRENCY_ROUTE = "change_favorite_currency"

        fun getScreenByRoute(route: String): ScreenRoute {
            return when (route) {
                PAIRS_ROUTE -> PairScreenRoute
                CONVERT_ROUTE -> ConvertScreenRoute
                SETTINGS_ROUTE -> SettingsScreenRoute
                CHANGE_FAVORITE_CURRENCY_ROUTE -> ChangeFavoriteCurrencyScreenRoute
                else -> PairScreenRoute
            }
        }
    }
}