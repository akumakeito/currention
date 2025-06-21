package com.akumakeito.commonui.presentation.items

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.akumakeito.core.util.log

@Composable
fun CurrencyRate(rate: Float, modifier: Modifier = Modifier) {
    log("rate: $rate")
    when {
        rate > 0 -> Text(
            modifier = modifier,
            text = String.format("+%.3f%%", rate),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End
        )

        rate < 0 -> Text(
            modifier = modifier,
            text = String.format("%.3f%%", rate),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.End

        )

        else -> Text(
            modifier = modifier,
            text = String.format("%.2f%%", rate),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.End
        )
    }

}