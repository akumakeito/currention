package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.akumakeito.currention.ui.items.CurrencyPairInExchangeRate
import ru.akumakeito.currention.viewmodel.PairCurrencyViewModel


@Composable
fun CurrencyExchangeRatesScreen(
    paddingValues: PaddingValues,
    pairViewModel: PairCurrencyViewModel
) {


    val currencyPairs by pairViewModel.currencyPairs.collectAsState()
    val isEditing by pairViewModel.isEditing.collectAsState()
    val editingPair by pairViewModel.editPairCurrency.collectAsState()
    val favoriteCurrencies by pairViewModel.favoriteCurrencies.collectAsState(emptyList())
    val allCurrencies by pairViewModel.fiatCurrencies.collectAsState(emptyList())
//    val searchingState by pairViewModel.searchingState.collectAsState()
    val state = rememberLazyListState()




    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            state = state
        ) {
            items(currencyPairs, key = {it.id}) { item ->

                CurrencyPairInExchangeRate(
                    pairCurrency = item,
                    onEditStateChange = { editingPair.id == item.id },
                    onDeletePairClickListener = { pairViewModel.deletePairById(item.id) },
                    favoriteCurrencyList = favoriteCurrencies,
                    allCurrencies = allCurrencies,
                    onCurrencyFromDropDownClickListener = {selectedCurrency ->
                        pairViewModel.updatePairCurrencyFrom(selectedCurrency)
                    },
                    onCurrencyToDropDownClickListener = {selectedCurrency ->
                        pairViewModel.updatePairCurrencyTo(selectedCurrency)
                    },
                    onEditPairClickListener = { pairViewModel.editPair(item) },
                    editingPair = editingPair,
                    onSearchTextChanged = {pairViewModel.onSearchTextChange(it)},
//                    searchingString = searchingState.searchText
                    )
            }

        }


    }
}






