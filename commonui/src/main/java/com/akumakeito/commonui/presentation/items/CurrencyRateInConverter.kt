package com.akumakeito.commonui.presentation.items

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import com.akumakeito.commonres.R
import com.akumakeito.commonui.presentation.util.formatWithRange
import com.akumakeito.core.models.domain.FiatCurrency

@Composable
fun CurrencyRateInConverter(
    currencyFrom: FiatCurrency,
    currencyTo: FiatCurrency,
    rate: Double,
    amount: Double?,
    readOnly: Boolean,
    onAmountDone: () -> Unit,
    onAmountTextChanged: (String) -> Unit,
    onClearAmount: () -> Unit,
    modifier: Modifier = Modifier

) {
    var value by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = amount) {
        value = amount?.let { formatWithRange(it) } ?: ""
    }

    val focusRequester = remember { FocusRequester() }

    if (!readOnly) {
        LaunchedEffect(Unit) {
            value = ""
            focusRequester.requestFocus()

        }
    }



    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        Text(
            text = stringResource(
                id = R.string.rate_for_one_currency_from,
                currencyFrom.shortCode,
                rate,
                currencyTo.shortCode
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )

        AmountTextField(
            value = value,
            readOnly = readOnly,
            onAmountTextChanged = onAmountTextChanged,
            onClearAmount = onClearAmount,
            focusRequester = focusRequester,
        )
    }
}