package ru.akumakeito.currention.ui.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.akumakeito.currention.R
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrencyPairInExchangeRate(
    pairCurrency: PairCurrency,
    onEditStateChange: () -> Boolean,
    currencyList: List<FiatCurrency>,
    editingPair: PairCurrency,
    onCurrencyFromDropDownClickListener: (FiatCurrency) -> Unit,
    onCurrencyToDropDownClickListener: (FiatCurrency) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {


    var isEditState by remember {
        mutableStateOf(onEditStateChange())
    }

    var currencyPair by remember {
        mutableStateOf(pairCurrency)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = onEditStateChange()) {
        isEditState = onEditStateChange()
    }

    LaunchedEffect(key1 = editingPair) {
        if (currencyPair.id == editingPair.id) {
            currencyPair = editingPair
        }
    }

    var currencyListState by remember {
        mutableStateOf(currencyList)
    }

    LaunchedEffect(key1 = currencyList) {
        currencyListState = currencyList
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .pointerInput(true) {
                detectTapGestures(onPress = {
                    expanded = true
                })
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        CurrencyFlagAmountShortCode(
            fiatCurrency = currencyPair.fromCurrency,
            amount = "1",
            isEditing = isEditState,
            currencyList = currencyListState,
            onCurrencyItemDropDownClickListener = onCurrencyFromDropDownClickListener,
            onSearchTextChanged = { onSearchTextChanged(it) },
            modifier = modifier.imePadding()
        )

        Image(
            painter = painterResource(id = R.drawable.equals),
            contentDescription = stringResource(R.string.equals),
            modifier = Modifier
                .padding(horizontal = 0.dp)
                .align(Alignment.CenterVertically)
        )


        CurrencyFlagAmountShortCode(
            fiatCurrency = currencyPair.toCurrency,
            amount = "${pairCurrency.toCurrencyNewRate}",
            isEditing = isEditState,
            currencyList = currencyListState,
            onCurrencyItemDropDownClickListener = onCurrencyToDropDownClickListener,
            onSearchTextChanged = { onSearchTextChanged(it) },
            modifier = modifier.imePadding()
        )



        if (!isEditState) {

            CurrencyRate(rate = pairCurrency.rateCurrency)
        }
    }
}

//    DropdownMenu(
//        expanded = expanded,
//        onDismissRequest = { expanded = false },
//        modifier = Modifier.background(color = MaterialTheme.colorScheme.tertiaryContainer)
//    ) {
//
//        DropdownMenuItem(
//            text = {
//                Text(
//                    text = stringResource(R.string.edit)
//                )
//            },
//            onClick = {
//                onEditPairClickListener()
//                expanded = false
//            },
//            leadingIcon = {
//                Icon(
//                    Icons.Default.Create,
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.outline
//                )
//            })
//        DropdownMenuItem(
//            text = {
//                Text(
//                    text = stringResource(R.string.delete)
//                )
//            },
//            onClick = {
//                onDeletePairClickListener()
//                expanded = false
//            },
//            leadingIcon = {
//                Icon(
//                    Icons.Default.Delete,
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.outline
//                )
//            })
//
//    }





