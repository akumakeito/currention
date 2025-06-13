package com.akumakeito.commonmodels

import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonres.R

val popularCurrencyShortCodeList =
    listOf("USD", "EUR", "RUB", "CNY", "JPY", "GBP", "GEL", "CHF", "TRY", "SEK")

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
    2,
    "Российский рубль",
    "RUB",
    "643",
    "P",
    R.drawable.flag_rub
)

val lari = FiatCurrency(
    3,
    "Грузинский лари",
    "GEL",
    "981",
    "L",
    R.drawable.flag_gel
)
