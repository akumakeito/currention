package ru.akumakeito.currention.util

import ru.akumakeito.currention.R
import ru.akumakeito.currention.domain.FiatCurrency


class Constants {

    companion object {
        val popularCurrencyShortCodeList = listOf("USD", "EUR", "RUB", "CNY", "JPY", "GBP", "GEL", "CHF", "TRY", "SEK")

        val CHOSEN_CURRENCY_KEY = "chosen currency"

        val usd = FiatCurrency(
            1,
            "Доллар США",
            "USD",
            "840",
            "$",
            R.drawable.flag_usd
        )

        val rub = FiatCurrency(
            1,
            "Российский рубль",
            "RUB",
            "643",
            "P",
            R.drawable.flag_rub
        )

    }
}