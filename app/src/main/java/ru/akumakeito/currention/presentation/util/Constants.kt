package ru.akumakeito.currention.presentation.util

import ru.akumakeito.currention.R
import ru.akumakeito.currention.data.network.dto.ConvertingPairCurrency
import ru.akumakeito.currention.domain.model.FiatCurrency
import ru.akumakeito.currention.domain.model.PairCurrency


class Constants {

    companion object {
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
            1,
            "Российский рубль",
            "RUB",
            "643",
            "P",
            R.drawable.flag_rub
        )

        val newPair =
            PairCurrency(
                id = 0,
                fromCurrency = usd,
                toCurrency = rub,
                toCurrencyLastRate = 0.0,
                toCurrencyNewRate = 0.0,
                rateCurrency = 0.0f
            )

        val convertingCurrency = ConvertingPairCurrency(
            firstCurrency = usd,
            secondCurrency = rub,
            rateFromFirstToSecond = 0.0,
            rateFromSecondToFirst = 0.0,
            amount = 0.0,
            rateByAmount = 0.0

        )

    }
}