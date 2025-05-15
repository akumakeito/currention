package com.akumakeito.commonui.presentation.items

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.akumakeito.commonmodels.domain.FiatCurrency

@Composable
fun FlagWithShortCode(fiatCurrency: FiatCurrency) {
    FlagItem(flagId = fiatCurrency.flag, size = 24)
    Text(text = fiatCurrency.shortCode)
}