package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.akumakeito.currention.PairCurrencyViewModel
import ru.akumakeito.currention.R
import ru.akumakeito.currention.ui.items.CurrencyPairInExchangeRate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyExchangeRatesScreen(
    paddingValues: PaddingValues,
    pairViewModel: PairCurrencyViewModel
) {


    val currencyPairs by pairViewModel.currencyPairs.collectAsState()
    val isEditing by pairViewModel.isEditing.collectAsState()
    val editingPair by pairViewModel.editPairCurrency.collectAsState()
    val favoriteCurrencies by pairViewModel.favoriteCurrencies.collectAsState(emptyList())



    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
        ) {
            items(currencyPairs, key = {it.id}) { item ->

                CurrencyPairInExchangeRate(
                    pairCurrency = item,
                    onEditStateChange = { editingPair.id == item.id },
                    onDeletePairClickListener = { pairViewModel.deletePairById(item.id) },
                    favoriteCurrencyList = favoriteCurrencies,
                    onCurrencyFromDropDownClickListener = {selectedCurrency ->
                        pairViewModel.updatePairCurrencyFrom(selectedCurrency)
                    },
                    onCurrencyToDropDownClickListener = {selectedCurrency ->
                        pairViewModel.updatePairCurrencyTo(selectedCurrency)
                    },
                    onEditPairClickListener = { pairViewModel.editPair(item) },
                    editingPair = editingPair
                    )
            }

        }

        if (isEditing) {
            Button(
                onClick = { pairViewModel.updatePair() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.done),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}






