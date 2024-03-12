package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.akumakeito.currention.CurrencyViewModel
import ru.akumakeito.currention.ui.items.CurrencyPairInExchangeRate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyExchangeRatesScreen(
    paddingValues: PaddingValues,
    currencyViewModel: CurrencyViewModel = hiltViewModel()
) {

    val currencyPairs by currencyViewModel.currencyPairs.collectAsState(emptyList())




    Box(Modifier.padding(paddingValues)) {
        LazyColumn(
        ) {
            items(currencyPairs, key = { it.id }) { item ->
                CurrencyPairInExchangeRate(
                    pairCurrency = item,
                    isEditing = false,
                    onEditPairClickListener = {},
                    onDeletePairClickListener = {},
                    onCurrencyDropDownClickListener = {})
            }

        }
    }


}



