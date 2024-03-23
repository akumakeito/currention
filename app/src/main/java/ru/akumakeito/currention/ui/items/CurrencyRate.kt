package ru.akumakeito.currention.ui.items

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CurrencyRate(rate: Float, modifier: Modifier = Modifier) {

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