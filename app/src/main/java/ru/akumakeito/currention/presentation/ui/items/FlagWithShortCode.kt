package ru.akumakeito.currention.ui.items

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.akumakeito.currention.domain.FiatCurrency

@Composable
fun FlagWithShortCode(fiatCurrency: FiatCurrency) {
    FlagItem(flagId = fiatCurrency.flag, size = 24)
    Text(text = fiatCurrency.shortCode)
}