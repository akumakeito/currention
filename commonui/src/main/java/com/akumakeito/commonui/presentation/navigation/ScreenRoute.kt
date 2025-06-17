package com.akumakeito.commonui.presentation.navigation

import com.akumakeito.commonres.R

sealed class ScreenRoute(
    val route: String,
    val titleResId: Int
) {
    object PairScreenRoute : ScreenRoute(PAIRS_ROUTE, R.string.currency_rates)
    object ConvertScreenRoute : ScreenRoute(CONVERT_ROUTE, R.string.converter)
    object SettingsScreenRoute : ScreenRoute(SETTINGS_ROUTE, R.string.settings)
    object SettingsListScreenRoute : ScreenRoute(SETTINGS_LIST_ROUTE, R.string.settings)
    object StartingScreenRoute : ScreenRoute(STARTING_ROUTE, R.string.app_name)
    object ChangeFavoriteCurrencyScreenRoute :
        ScreenRoute(CHANGE_FAVORITE_CURRENCY_ROUTE, R.string.favorite_currencies)
    object ErrorScreenRoute : ScreenRoute(ERROR_ROUTE, R.string.error)


    companion object {
        const val PAIRS_ROUTE = "pairs"
        const val CONVERT_ROUTE = "convert"
        const val SETTINGS_ROUTE = "settings"
        const val SETTINGS_LIST_ROUTE = "settings_list"
        const val CHANGE_FAVORITE_CURRENCY_ROUTE = "change_favorite_currency"
        const val STARTING_ROUTE = "starting"
        const val ERROR_ROUTE = "error"

        fun getScreenByRoute(route: String): ScreenRoute {
            return when (route) {
                PAIRS_ROUTE -> PairScreenRoute
                CONVERT_ROUTE -> ConvertScreenRoute
                SETTINGS_ROUTE -> SettingsScreenRoute
                CHANGE_FAVORITE_CURRENCY_ROUTE -> ChangeFavoriteCurrencyScreenRoute
                SETTINGS_LIST_ROUTE -> SettingsListScreenRoute
                STARTING_ROUTE -> StartingScreenRoute
                ERROR_ROUTE -> ErrorScreenRoute
                else -> PairScreenRoute
            }
        }
    }
}