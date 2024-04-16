package ru.akumakeito.currention.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.akumakeito.currention.domain.FiatCurrency

@Composable
fun ConvertingCurrencyRow(
    firstCurrency: FiatCurrency,
    secondCurrency: FiatCurrency,
    rate: Double,
    readOnly : Boolean,
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
        Spacer(modifier = Modifier.weight(1f))

        CurrencyRateInConverter(
            currencyFrom = firstCurrency,
            currencyTo = secondCurrency,
            rate = rate,
            readOnly = readOnly,
            amount = amount
        )
    }

}