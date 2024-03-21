package ru.akumakeito.currention.ui.items

import SpacerWidth
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.akumakeito.currention.R
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency
import ru.akumakeito.currention.ui.theme.CurrentionTheme


@Composable
fun CurrencyPairInExchangeRate(
    pairCurrency: PairCurrency,
    onEditStateChange: () -> Boolean,
    favoriteCurrencyList: List<FiatCurrency>,
    editingPair : PairCurrency,
    onCurrencyFromDropDownClickListener: (FiatCurrency) -> Unit,
    onCurrencyToDropDownClickListener: (FiatCurrency) -> Unit,
    onEditPairClickListener: () -> Unit,
    onDeletePairClickListener: () -> Unit
) {


    var isEditState by remember {
        mutableStateOf(onEditStateChange())
    }

    var currencyPair by remember {
        mutableStateOf(pairCurrency)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = onEditStateChange()) {
        isEditState = onEditStateChange()
    }

    LaunchedEffect(key1 = editingPair) {
        if (currencyPair.id == editingPair.id) {
            currencyPair = editingPair
        }

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .pointerInput(true) {
                detectTapGestures(onLongPress = {
                    expanded = true
                })
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        CurrencyFlagAmountShortCode(
            fiatCurrency = currencyPair.fromCurrency,
            amount = "1",
            isEditing = isEditState,
            favoriteCurrencyList = favoriteCurrencyList,
            onCurrencyItemDropDownClickListener = { selectedCurrency -> onCurrencyFromDropDownClickListener(selectedCurrency) }
        )

        Image(
            painter = painterResource(id = R.drawable.equals),
            contentDescription = stringResource(R.string.equals),
            modifier = Modifier
                .padding(horizontal = 0.dp)
                .align(Alignment.CenterVertically)
        )

        CurrencyFlagAmountShortCode(
            fiatCurrency = currencyPair.toCurrency,
            amount = "${pairCurrency.toCurrencyNewRate}",
            isEditing = isEditState,
            favoriteCurrencyList = favoriteCurrencyList,
            onCurrencyItemDropDownClickListener = { selectedCurrency -> onCurrencyToDropDownClickListener(selectedCurrency)},
        )
        if (isEditState) {
            IconButton(onClick = { onDeletePairClickListener() }) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        } else {
            CurrencyRate(rate = pairCurrency.rateCurrency)
        }

    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.background(color = MaterialTheme.colorScheme.tertiaryContainer)
    ) {

        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.edit)
                )
            },
            onClick = {
                onEditPairClickListener()
                expanded = false
            },
            leadingIcon = {
                Icon(Icons.Default.Create, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
            })
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.delete)
                )
            },
            onClick = {
                onDeletePairClickListener()
                expanded = false
            },
            leadingIcon = {
                Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
            })

    }

}

@Composable
fun CurrencyFlagAmountShortCode(
    fiatCurrency: FiatCurrency,
    amount: String,
    isEditing: Boolean,
    onCurrencyItemDropDownClickListener: (FiatCurrency) -> Unit,
    favoriteCurrencyList: List<FiatCurrency>,
) {

    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()


    var isEditState by remember {
        mutableStateOf(isEditing)
    }

    var currency by remember {
        mutableStateOf(fiatCurrency)
    }

    LaunchedEffect(fiatCurrency) {
        currency = fiatCurrency
    }

    LaunchedEffect(key1 = isEditing) {
        isEditState = isEditing
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        CurrencyFlag(flagId = currency.flag)



        SpacerWidth(width = 8)
        if (isEditState) {

            Text(
                text = currency.shortCode,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        } else {
            Text(
                text = "$amount ${currency.shortCode}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }

        if (isEditState) {
            IconButton(onClick = {
                expanded = true
            }) {
                Icon(Icons.Default.ArrowDropDown, null, tint = MaterialTheme.colorScheme.outline)
            }


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },

                ) {

                favoriteCurrencyList.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(text = currency.shortCode) },
                        onClick = {
                            onCurrencyItemDropDownClickListener(currency)
                            expanded = false
                        },
                        leadingIcon = {
                            CurrencyFlag(flagId = currency.flag, 24)
                        },
                        modifier = Modifier.background(color = MaterialTheme.colorScheme.tertiaryContainer)
                    )
                }

                HorizontalDivider()
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.all_currencies)) },
                    onClick = { /* Handle send feedback! */ })
            }
        }
        LaunchedEffect(expanded) {
            if (expanded) {
                // Scroll to show the bottom menu items.
                scrollState.scrollTo(scrollState.maxValue)
            }
        }
    }
}


@Composable
fun CurrencyRate(rate: Float) {

    when {
        rate > 0 -> Text(
            text = "+${rate}%",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End
        )

        rate < 0 -> Text(
            text = "${rate}%",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.End

        )

        else -> Text(
            text = "${rate}%",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End
        )
    }

}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun CurrencyPairInExchangeRatePreview() {
    CurrentionTheme {

    }
}