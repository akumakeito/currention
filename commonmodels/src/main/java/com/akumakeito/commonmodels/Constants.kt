package com.akumakeito.commonmodels

import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonres.R

val popularCurrencyShortCodeList =
    listOf("USD", "EUR", "RUB", "CNY", "JPY", "GBP", "GEL", "CHF", "TRY", "SEK")

val CHOSEN_CURRENCY_KEY = "chosen currency"

val usd = FiatCurrency(
    id = 1,
    nameEn = "Доллар США",
    nameRu = "US Dollar",
    shortCode = "USD",
    code = "840",
    symbol = "$",
    flag = R.drawable.flag_usd
)

val rub = FiatCurrency(
    id = 2,
    nameEn = "Российский рубль",
    nameRu = "Russian Ruble",
    shortCode = "RUB",
    code = "643",
    symbol = "P",
    flag = R.drawable.flag_rub
)

val lari = FiatCurrency(
    id = 3,
    nameEn = "Грузинский лари",
    nameRu = "Georgian Lari",
    shortCode = "GEL",
    code = "981",
    symbol = "L",
    flag = R.drawable.flag_gel
)
