package com.akumakeito.convert.presentation.convert

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.akumakeito.commonui.presentation.navigation.ScreenRoute
import ru.akumakeito.currention.ui.items.CustomTopAppBar

@Composable
fun CurrencyConverterScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val favoritesConverterViewModel: FavCurrencyConverterViewModel = hiltViewModel()

    Scaffold(
        modifier = modifier.padding(paddingValues),
        topBar = {
            CustomTopAppBar(
                currentScreen = ScreenRoute.ConvertScreenRoute,
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column() {
                FavCurrencyConverterScreen(
                    viewModel = favoritesConverterViewModel
                )
            }
        }
    }
}
