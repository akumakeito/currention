package ru.akumakeito.currention.ui.items

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.akumakeito.currention.R

@Composable
fun CurrencyRateInConverter(
    convertingCurrency: ConvertingCurrency,
    amount: Int,
    modifier: Modifier = Modifier
) {
    var value by rememberSaveable {
        mutableStateOf(amount.toString())
    }

    Column(horizontalAlignment = Alignment.End) {
        Text(
            text = stringResource(
                id = R.string.rate_for_one_currency_from,
                convertingCurrency.fromCurrency.shortCode,
                convertingCurrency.rate,
                convertingCurrency.toCurrency.shortCode
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )

        TextField(
            value = value,
            onValueChange = { value = it },
            singleLine = true
        )


    }

}