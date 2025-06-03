package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akumakeito.commonres.R
import com.akumakeito.commonui.presentation.items.ConvertingCurrencyRow
import com.akumakeito.convert.presentation.convert.ConvertCurrencyViewModel
import kotlinx.coroutines.launch


@Composable
fun CurrencyConverterScreen(
    paddingValues: PaddingValues,
) {
    val convertCurrencyViewModel: ConvertCurrencyViewModel = hiltViewModel()

    val convertingCurrencyState by convertCurrencyViewModel.convertingCurrencyState.collectAsState()
    val currencyList by convertCurrencyViewModel.fiatCurrencyList.collectAsState(emptyList())


    val uiState = convertCurrencyViewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()


    Box(modifier = Modifier.padding(paddingValues)) {
        if (uiState.value.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Column(modifier = Modifier.padding(24.dp)) {

            ConvertingCurrencyRow(
                firstCurrency = convertingCurrencyState.firstCurrency,
                secondCurrency = convertingCurrencyState.secondCurrency,
                rate = convertingCurrencyState.rateFromFirstToSecond,
                amount = convertingCurrencyState.amount,
                readOnly = false,
                currencyList = currencyList,
                onCurrencyItemDropDownClickListener = { selectedCurrency ->
                    convertCurrencyViewModel.updatePairCurrencyFrom(selectedCurrency)
                },
                onSearchTextChanged = { convertCurrencyViewModel.onSearchTextChange(it) },
                onAmountTextChanged = { amount ->
                    coroutineScope.launch {
                        convertCurrencyViewModel.changeAmount(amount)
                    }
                },
                onAmountDone = { convertCurrencyViewModel.convert() },
                onClearAmount = { convertCurrencyViewModel.clearAmount() }
            )

            Box(contentAlignment = Alignment.Center) {

                HorizontalDivider()
                IconButton(onClick = { convertCurrencyViewModel.reversePairCurrency() }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_button_swap),
                        contentDescription = null
                    )
                }
            }

            ConvertingCurrencyRow(
                firstCurrency = convertingCurrencyState.secondCurrency,
                secondCurrency = convertingCurrencyState.firstCurrency,
                rate = convertingCurrencyState.rateFromSecondToFirst,
                amount = convertingCurrencyState.convertedAmount,
                readOnly = true,
                currencyList = currencyList,
                onCurrencyItemDropDownClickListener = { selectedCurrency ->
                    convertCurrencyViewModel.updatePairCurrencyTo(selectedCurrency)
                },
                onSearchTextChanged = { convertCurrencyViewModel.onSearchTextChange(it) },
                onAmountTextChanged = {},
                onAmountDone = {},
                onClearAmount = {}

            )
            HorizontalDivider()


//            KeyboardScreen()
//            NumberKeyboard(
//                onNumberClick = { amount ->
//                    coroutineScope.launch {
//                        convertCurrencyViewModel.changeAmount(amount)
//                    }
//                },
//                onDoneClick = { convertCurrencyViewModel.convert() },
//                onBackspaceClick = {
//                    coroutineScope.launch {
//                        convertCurrencyViewModel.changeAmount("")
//                    }
//                }
//            )
        }
    }
}
