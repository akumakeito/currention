package com.akumakeito.convert.presentation.convert.items

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.akumakeito.core.models.domain.FiatCurrency
import com.akumakeito.commonui.presentation.items.CurrencyFlagAmountShortCode
import com.akumakeito.commonui.presentation.items.CurrencyRateInConverter
import com.akumakeito.core.util.log

@Composable
fun ConvertingCurrencyRow(
    firstCurrency: FiatCurrency,
    secondCurrency: FiatCurrency,
    rate: Double,
    readOnly: Boolean,
    amount: Double?,
    onCurrencyItemDropDownClickListener: (FiatCurrency) -> Unit,
    currencyList: List<FiatCurrency>,
    onSearchTextChanged: (String) -> Unit,
    onAmountTextChanged: (String) -> Unit,
    onAmountDone: () -> Unit,
    onClearAmount: () -> Unit,
    modifier: Modifier = Modifier
) {
    var amountState by rememberSaveable {
        mutableStateOf(amount)
    }

    LaunchedEffect(key1 = amount) {
        amountState = amount
        log("amountState: ${amountState}")
    }



    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        CurrencyFlagAmountShortCode(
            fiatCurrency = firstCurrency,
            amount = "",
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
            amount = amountState,
            onAmountTextChanged = { onAmountTextChanged(it) },
            onAmountDone = { onAmountDone() },
            onClearAmount = { onClearAmount() }
        )
    }

}