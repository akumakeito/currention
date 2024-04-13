package ru.akumakeito.currention.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.akumakeito.currention.ui.items.ConvertingCurrencyRow
import ru.akumakeito.currention.viewmodel.ConvertCurrencyViewModel
import ru.akumakeito.currention.viewmodel.SearchingViewModel

@Composable
fun CurrencyConverterScreen(
    convertCurrencyViewModel: ConvertCurrencyViewModel,
    searchingViewModel: SearchingViewModel
) {

    val convertingCurrencyState by convertCurrencyViewModel.convertingCurrencyState.collectAsState()
    val currencyList by searchingViewModel.fiatCurrencies.collectAsState(emptyList())



    ConvertingCurrencyRow(
        currency = convertingCurrencyState.fromCurrency,
        pairCurrency = convertingCurrencyState,
        amount = 1,
        currencyList = currencyList,
        onCurrencyItemDropDownClickListener = { selectedCurrency ->
            convertCurrencyViewModel.updatePairCurrencyFrom(selectedCurrency) },
        onSearchTextChanged = { searchingViewModel.onSearchTextChange(it) },
    )

    ConvertingCurrencyRow(
        currency = convertingCurrencyState.toCurrency,
        pairCurrency = convertingCurrencyState,
        amount = 1,
        currencyList = currencyList,
        onCurrencyItemDropDownClickListener = { selectedCurrency ->
            convertCurrencyViewModel.updatePairCurrencyFrom(selectedCurrency) },
        onSearchTextChanged = { searchingViewModel.onSearchTextChange(it) },
    )



}