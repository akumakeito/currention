package ru.akumakeito.currention.ui.items

import SpacerWidth
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.akumakeito.currention.CurrencyListMain
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
    rateCurrency = -0.75f
)

@Composable
fun CurrencyPairInExchangeRate(
    pairCurrency: PairCurrency
) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween

    ) {

       CurrencyFlag(flagId = pairCurrency.fromCurrency.flag)

      //  SpacerWidth(width = 12)

        Text(text = "1 ${pairCurrency.fromCurrency.shortCode}")

        Image(
            painter = painterResource(id = R.drawable.equals),
            contentDescription = stringResource(R.string.equals),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterVertically)
        )

        CurrencyFlag(flagId = pairCurrency.toCurrency.flag)

        Text(text = "${pairCurrency.toCurrencyNewRate} ${pairCurrency.fromCurrency.shortCode}")

        CurrencyRate(rate = pairCurrency.rateCurrency, modifier = Modifier.weight(1f))

    }
    
}

@Composable
fun CurrencyRate(rate : Float, modifier: Modifier = Modifier) {

    when {
        rate > 0 -> Text(text = "+${rate}%", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        rate < 0 -> Text(text = "${rate}%", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.error)
        else -> Text(text = "${rate}%", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
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

        CurrencyPairInExchangeRate(example)
    }
}