package ru.akumakeito.currention.ui.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonres.R
import com.akumakeito.commonui.presentation.Dimens
import com.akumakeito.commonui.presentation.items.CurrencyFlagAmountShortCode
import com.akumakeito.commonui.presentation.items.CurrencyRate
import com.akumakeito.rates.domain.PairCurrency


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrencyPairInExchangeRate(
    pairCurrency: PairCurrency,
    onEditStateChange: () -> Boolean,
    currencyList: List<FiatCurrency>,
    editingPair: PairCurrency,
    onCurrencyFromDropDownClickListener: (FiatCurrency) -> Unit,
    onCurrencyToDropDownClickListener: (FiatCurrency) -> Unit,
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

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(true) {
                detectTapGestures(onPress = {
                    expanded = true
                })
            },
    ) {

        val (currencyFrom, equalSign, currencyTo, rate) = createRefs()

        CurrencyFlagAmountShortCode(
            fiatCurrency = currencyPair.fromCurrency,
            amount = "1",
            isEditing = isEditState,
            currencyList = currencyList,
            onCurrencyItemDropDownClickListener = onCurrencyFromDropDownClickListener,
            onSearchTextChanged = {},
            modifier = Modifier.constrainAs(currencyFrom) {
                top.linkTo(parent.top, margin = Dimens.x2)
                bottom.linkTo(parent.bottom, margin = Dimens.x2)
                start.linkTo(parent.start, margin = Dimens.x3)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.equals),
            contentDescription = stringResource(R.string.equals),
            modifier = Modifier
                .constrainAs(equalSign) {
                    start.linkTo(currencyFrom.end, margin = Dimens.x2)
                    top.linkTo(currencyFrom.top)
                    bottom.linkTo(currencyFrom.bottom)
                }
        )

        CurrencyFlagAmountShortCode(
            fiatCurrency = currencyPair.toCurrency,
            amount = String.format("%.2f", pairCurrency.toCurrencyNewRate),
            isEditing = isEditState,
            currencyList = currencyList,
            onCurrencyItemDropDownClickListener = onCurrencyToDropDownClickListener,
            onSearchTextChanged = { },
            modifier = Modifier.constrainAs(currencyTo) {
                top.linkTo(currencyFrom.top)
                bottom.linkTo(currencyFrom.bottom)
                start.linkTo(equalSign.end, margin = Dimens.x2)
            }
        )

        if (!isEditState) {
            CurrencyRate(
                rate = pairCurrency.rateCurrency ?: 0.0f,
                modifier = Modifier.constrainAs(rate) {
                    top.linkTo(currencyFrom.top)
                    bottom.linkTo(currencyFrom.bottom)
                    end.linkTo(parent.end, margin = Dimens.x3)
                }
            )
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





