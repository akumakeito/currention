package ru.akumakeito.currention.ui.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import ru.akumakeito.currention.R
import ru.akumakeito.currention.domain.FiatCurrency

@Composable
fun CurrencyRateInConverter(
    currencyFrom: FiatCurrency, currencyTo: FiatCurrency,
    rate: Double,
    amount: Int,
    readOnly: Boolean,
    modifier: Modifier = Modifier
) {
    var value by rememberSaveable {
        mutableStateOf(amount.toString())
    }

    Column(horizontalAlignment = Alignment.End) {
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

        TextField(
            value = value,
            onValueChange = { value = it },
            singleLine = true,
            modifier = Modifier
                .align(Alignment.End)
                .wrapContentWidth(Alignment.End),
            textStyle = MaterialTheme.typography.titleLarge,
            readOnly = readOnly,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                disabledIndicatorColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
            )

        )


    }

}