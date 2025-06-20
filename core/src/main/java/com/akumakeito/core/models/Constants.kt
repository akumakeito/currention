package com.akumakeito.core.models

import com.akumakeito.commonres.R
import com.akumakeito.core.models.domain.FiatCurrency

val popularCurrencyShortCodeList =
    listOf("USD", "EUR", "RUB", "CNY", "JPY", "GBP", "GEL", "CHF", "TRY", "SEK")

val usd = FiatCurrency(
    nameEn = "Доллар США",
    nameRu = "US Dollar",
    shortCode = "USD",
    code = "840",
    symbol = "$",
    flag = R.drawable.flag_usd
)

val rub = FiatCurrency(
    nameEn = "Российский рубль",
    nameRu = "Russian Ruble",
    shortCode = "RUB",
    code = "643",
    symbol = "P",
    flag = R.drawable.flag_rub
)

val lari = FiatCurrency(
    nameEn = "Грузинский лари",
    nameRu = "Georgian Lari",
    shortCode = "GEL",
    code = "981",
    symbol = "L",
    flag = R.drawable.flag_gel
)
