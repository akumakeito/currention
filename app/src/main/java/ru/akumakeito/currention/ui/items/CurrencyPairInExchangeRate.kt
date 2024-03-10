package ru.akumakeito.currention.ui.items

import SpacerWidth
import android.content.res.Configuration
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import ru.akumakeito.currention.ui.screens.rub
import ru.akumakeito.currention.ui.screens.usd
import ru.akumakeito.currention.ui.theme.CurrentionTheme

val example = PairCurrency(
    id = 1,
    fromCurrency = usd,
    toCurrency = rub,
    toCurrencyLastRate = 91.6358,
    toCurrencyNewRate = 90.9500,
    rateCurrency = 0.0f
)

@Composable
fun CurrencyPairInExchangeRate(
    pairCurrency: PairCurrency,
    isEditing: Boolean = true,
    onCurrencyDropDownClickListener : (FiatCurrency) -> Unit,
    onDeletePairClickListener : () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CurrencyFlagAmountShortCode(fiatCurrency = pairCurrency.fromCurrency, amount = "1",
            isEditing = isEditing,
            onCurrencyDropDownClickListener = { onCurrencyDropDownClickListener(pairCurrency.fromCurrency) }
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
            isEditing = isEditing,
            onCurrencyDropDownClickListener = {onCurrencyDropDownClickListener(pairCurrency.toCurrency)},
        )
        if (isEditing) {
            IconButton(onClick = { onDeletePairClickListener }) {
                Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
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
    isEditing: Boolean = true,
    onCurrencyDropDownClickListener : (FiatCurrency) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CurrencyFlag(flagId = fiatCurrency.flag)

        SpacerWidth(width = 8)
        if (isEditing) {
        Text(
            text = fiatCurrency.shortCode,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline
        ) } else {
            Text(
                text = "$amount ${fiatCurrency.shortCode}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }

        if (isEditing) {
            IconButton(onClick = { onCurrencyDropDownClickListener(fiatCurrency) }) {
                Icon(Icons.Default.ArrowDropDown, null, tint = MaterialTheme.colorScheme.outline)
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