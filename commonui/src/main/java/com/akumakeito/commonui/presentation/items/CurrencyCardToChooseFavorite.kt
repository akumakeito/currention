package com.akumakeito.commonui.presentation.items

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.akumakeito.core.models.domain.FiatCurrency

@Composable
fun CurrencyCardToChooseFavorite(
    modifier: Modifier = Modifier,
    currency: FiatCurrency,
    onCheckboxClickListener: () -> Unit,
) {
    var checkState by rememberSaveable {
        mutableStateOf(currency.isFavorite)
    }
    val context = LocalContext.current
    val stringId = "cur${currency.shortCode.lowercase()}"
    val currencyNameId = context.resources.getIdentifier(stringId, "string", context.packageName)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FlagItem(flagId = currency.flag)
            SpacerWidth(width = 16)
            Text(
                text = stringResource(currencyNameId) + " (${currency.shortCode})",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )

            SpacerWidth(width = 16)

            Checkbox(checked = checkState, onCheckedChange = {
                checkState = it
                onCheckboxClickListener()
            })
        }
    }
}