package ru.akumakeito.currention.ui.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency
import ru.akumakeito.currention.ui.screens.rub
import ru.akumakeito.currention.ui.screens.usd

val example = PairCurrency(
    id = 1,
    fromCurrency = usd,
    toCurrency = rub,
    toCurrencyLastRate = 91.6358,
    toCurrencyNewRate = 90.9500,
    rateCurrency = -0.75f

)
@Preview
@Composable
fun CurrencyPairInExchangeRate(
    pairCurrency: PairCurrency
) {




}