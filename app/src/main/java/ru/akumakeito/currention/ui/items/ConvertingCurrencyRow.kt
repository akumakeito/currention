package ru.akumakeito.currention.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency

@Composable
fun ConvertingCurrencyRow(
    currency: FiatCurrency,
    pairCurrency: PairCurrency,
    amount: Int,
    onCurrencyItemDropDownClickListener: (FiatCurrency) -> Unit,
    currencyList: List<FiatCurrency>,
    onSearchTextChanged: (String) -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CurrencyFlagAmountShortCode(
            fiatCurrency = currency,
            amount = amount.toString(),
            isEditing = true,
            onCurrencyItemDropDownClickListener = onCurrencyItemDropDownClickListener,
            currencyList = currencyList,
            onSearchTextChanged = onSearchTextChanged
        )

        CurrencyRateInConverter(
            currencyFrom = pairCurrency.fromCurrency,
            currencyTo = pairCurrency.toCurrency,
            rate = pairCurrency.toCurrencyNewRate,
            amount = amount
        )
    }

}