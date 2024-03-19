package ru.akumakeito.currention.ui.items

import SpacerWidth
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
//    onCurrencyDropDownClickListener: (currencyToChange : FiatCurrency, chosenCurrency :FiatCurrency) -> Unit,
    onDeletePairClickListener: () -> Unit
) {


    var isEditState by remember {
        mutableStateOf(onEditStateChange())
    }

    LaunchedEffect(key1 = onEditStateChange()) {
        isEditState = onEditStateChange()
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CurrencyFlagAmountShortCode(
            fiatCurrency = pairCurrency.fromCurrency,
            amount = "1",
            isEditing = isEditState,
            favoriteCurrencyList = favoriteCurrencyList,
//            onCurrencyItemDropDownClickListener = { onCurrencyDropDownClickListener(pairCurrency.fromCurrency, it) }
        )

        Image(
            painter = painterResource(id = R.drawable.equals),
            contentDescription = stringResource(R.string.equals),
            modifier = Modifier
                .padding(horizontal = 0.dp)
                .align(Alignment.CenterVertically)
        )

        CurrencyFlagAmountShortCode(
            fiatCurrency = pairCurrency.toCurrency,
            amount = "${pairCurrency.toCurrencyNewRate}",
            isEditing = isEditState,
            favoriteCurrencyList = emptyList(),
//            onCurrencyItemDropDownClickListener = { onCurrencyDropDownClickListener(pairCurrency.toCurrency) },
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

}

@Composable
fun CurrencyFlagAmountShortCode(
    fiatCurrency: FiatCurrency,
    amount: String,
    isEditing: Boolean,
    favoriteCurrencyList: List<FiatCurrency>,
//    onCurrencyItemDropDownClickListener: () -> FiatCurrency

) {

    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()


    var isEditState by remember {
        mutableStateOf(isEditing)
    }

    LaunchedEffect(key1 = isEditing) {
        isEditState = isEditing
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        CurrencyFlag(flagId = fiatCurrency.flag)

        SpacerWidth(width = 8)
        if (isEditState) {
            Text(
                text = fiatCurrency.shortCode,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        } else {
            Text(
                text = "$amount ${fiatCurrency.shortCode}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }

        if (isEditState) {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.ArrowDropDown, null, tint = MaterialTheme.colorScheme.outline)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                scrollState = scrollState
            ) {
                favoriteCurrencyList.forEach { currency ->

                    DropdownMenuItem(
                        text = { Text(text = currency.shortCode) },
                        onClick = { },
                        leadingIcon = {
                            CurrencyFlag(flagId = currency.flag, 24)
                        })
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