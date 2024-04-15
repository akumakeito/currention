package ru.akumakeito.currention.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import ru.akumakeito.currention.domain.FiatCurrency

@Composable
fun ConvertingCurrencyRow(
    firstCurrency: FiatCurrency,
    secondCurrency: FiatCurrency,
    rate: Double,
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
            fiatCurrency = firstCurrency,
            amount = amount.toString(),
            isEditing = true,
            onCurrencyItemDropDownClickListener = onCurrencyItemDropDownClickListener,
            currencyList = currencyList,
            onSearchTextChanged = onSearchTextChanged
        )

        CurrencyRateInConverter(
            currencyFrom = firstCurrency,
            currencyTo = secondCurrency,
            rate = rate,
            amount = amount
        )
    }

}