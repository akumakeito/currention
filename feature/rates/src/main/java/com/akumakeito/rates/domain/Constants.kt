package com.akumakeito.rates.domain

import com.akumakeito.commonmodels.rub
import com.akumakeito.commonmodels.usd

val newPair =
    PairCurrency(
        id = 0,
        fromCurrency = usd,
        toCurrency = rub,
        toCurrencyLastRate = 0.0,
        toCurrencyNewRate = 0.0,
        rateCurrency = 0.0f
    )