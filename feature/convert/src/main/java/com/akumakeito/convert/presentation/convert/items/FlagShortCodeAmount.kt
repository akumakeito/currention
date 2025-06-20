package com.akumakeito.convert.presentation.convert.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.akumakeito.core.models.domain.FiatCurrency
import com.akumakeito.commonui.presentation.Dimens
import com.akumakeito.commonui.presentation.items.FlagItem
import com.akumakeito.commonui.presentation.util.format


@Composable
fun FlagShortCodeAmount(
    isLoading: Boolean,
    currency: FiatCurrency,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.x1),
        modifier = modifier.padding(top = Dimens.x3),
    ) {
        FlagItem(flagId = currency.flag)
        Text(
            text = currency.shortCode,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = currency.currentRate?.format(2) ?: "0.0",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}