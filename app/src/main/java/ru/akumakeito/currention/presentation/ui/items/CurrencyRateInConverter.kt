package ru.akumakeito.currention.ui.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.akumakeito.currention.R
import ru.akumakeito.currention.domain.model.FiatCurrency
import ru.akumakeito.currention.presentation.util.formatWithRange

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

        TextField(
            value = value,
            onValueChange = {
                value = it
                onAmountTextChanged(value)
            },
            singleLine = true,
            modifier = Modifier
                .widthIn(1.dp, Dp.Infinity)
                .focusRequester(focusRequester),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End,
                fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
                fontSize = 24.sp
            ),
            readOnly = readOnly,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onDone = {
                onAmountDone()
            }),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                disabledIndicatorColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
            ),
            trailingIcon = {
                if (!readOnly) {
                    IconButton(onClick = { onClearAmount() }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        )
    }
}