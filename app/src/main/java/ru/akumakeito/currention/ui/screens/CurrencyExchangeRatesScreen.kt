package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import ru.akumakeito.currention.ui.items.CurrencyPairInExchangeRate
import ru.akumakeito.currention.viewmodel.PairCurrencyViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrencyExchangeRatesScreen(
    paddingValues: PaddingValues,
    pairViewModel: PairCurrencyViewModel,
    modifier: Modifier = Modifier
) {


    val currencyPairs by pairViewModel.currencyPairs.collectAsState()
    val isEditing by pairViewModel.isEditing.collectAsState()
    val editingPair by pairViewModel.editPairCurrency.collectAsState()
    val currencyList by pairViewModel.fiatCurrencies.collectAsState(emptyList())
    val state = rememberLazyListState()

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()



    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            state = state,
//            modifier = modifier
//                .bringIntoViewRequester(bringIntoViewRequester)
//                .onFocusEvent { focusState ->
//                    if (focusState.isFocused) {
//                        coroutineScope.launch {
//                            bringIntoViewRequester.bringIntoView()
//                        }
//                    }
//                }
        ) {
            itemsIndexed(currencyPairs) { index, item ->

                CurrencyPairInExchangeRate(
                    pairCurrency = item,
                    onEditStateChange = {
                        coroutineScope.launch {
                            state.animateScrollToItem(index)
                        }
                        editingPair.id == item.id
                    },
                    onDeletePairClickListener = { pairViewModel.deletePairById(item.id) },
                    currencyList = currencyList,
                    onCurrencyFromDropDownClickListener = { selectedCurrency ->
                        pairViewModel.updatePairCurrencyFrom(selectedCurrency)
                    },
                    onCurrencyToDropDownClickListener = { selectedCurrency ->
                        pairViewModel.updatePairCurrencyTo(selectedCurrency)
                    },
                    onEditPairClickListener = { pairViewModel.editPair(item) },
                    editingPair = editingPair,
                    onSearchTextChanged = { pairViewModel.onSearchTextChange(it) },
                )
            }

        }


    }
}






