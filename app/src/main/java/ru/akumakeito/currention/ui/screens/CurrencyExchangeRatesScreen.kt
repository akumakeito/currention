package ru.akumakeito.currention.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.akumakeito.currention.CurrencyViewModel
import ru.akumakeito.currention.R
import ru.akumakeito.currention.domain.FiatCurrency

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

@Composable
fun CurrencyExchangeRatesScreen(
    currencyViewModel: CurrencyViewModel = hiltViewModel()
) {
    Button(onClick = { currencyViewModel.getPairRates(usd, rub, 1)}) {

        Text(text = "Get pair rates")

    }
}