package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.akumakeito.currention.R
import ru.akumakeito.currention.ui.items.ConvertingCurrencyRow
import ru.akumakeito.currention.viewmodel.ConvertCurrencyViewModel
import ru.akumakeito.currention.viewmodel.SearchingViewModel

@Composable
fun CurrencyConverterScreen(
    paddingValues: PaddingValues,
    convertCurrencyViewModel: ConvertCurrencyViewModel,
    searchingViewModel: SearchingViewModel
) {

    val convertingCurrencyState by convertCurrencyViewModel.convertingCurrencyState.collectAsState()
    val currencyList by searchingViewModel.fiatCurrencies.collectAsState(emptyList())


    Box(modifier = Modifier.padding(paddingValues)) {

        Column(modifier = Modifier.padding(24.dp) ) {

            ConvertingCurrencyRow(
                firstCurrency = convertingCurrencyState.firstCurrency,
                secondCurrency = convertingCurrencyState.secondCurrency,
                rate = convertingCurrencyState.rateFromFirstToSecond,
                amount = 1,
                readOnly = false,
                currencyList = currencyList,
                onCurrencyItemDropDownClickListener = { selectedCurrency ->
                    convertCurrencyViewModel.updatePairCurrencyFrom(selectedCurrency)
                },
                onSearchTextChanged = { searchingViewModel.onSearchTextChange(it) },
            )

            Box(contentAlignment = Alignment.Center) {

                HorizontalDivider()
                IconButton(onClick = { /*TODO*/ }) {
                    Image(painter = painterResource(id = R.drawable.ic_button_swap), contentDescription = null)
                }
            }

            ConvertingCurrencyRow(
                firstCurrency = convertingCurrencyState.secondCurrency,
                secondCurrency = convertingCurrencyState.firstCurrency,
                rate = convertingCurrencyState.rateFromSecondToFirst,
                amount = 1,
                readOnly = true,
                currencyList = currencyList,
                onCurrencyItemDropDownClickListener = { selectedCurrency ->
                    convertCurrencyViewModel.updatePairCurrencyFrom(selectedCurrency)
                },
                onSearchTextChanged = { searchingViewModel.onSearchTextChange(it) },

            )

            HorizontalDivider()


        }

    }
}