package com.akumakeito.commonui.presentation.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.akumakeito.commonmodels.domain.FiatCurrency


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CurrencyFlagAmountShortCode(
    fiatCurrency: FiatCurrency,
    amount: String,
    isEditing: Boolean,
    onCurrencyItemDropDownClickListener: (FiatCurrency) -> Unit,
    currencyList: List<FiatCurrency>,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val scope = rememberCoroutineScope()


    var isEditState by remember {
        mutableStateOf(isEditing)
    }

    LaunchedEffect(key1 = isEditing) {
        isEditState = isEditing
    }

    var currency by remember {
        mutableStateOf(fiatCurrency)
    }

    LaunchedEffect(fiatCurrency) {
        currency = fiatCurrency
    }

    var currencyListState by remember {
        mutableStateOf(currencyList)
    }

    LaunchedEffect(currencyList) {
        currencyListState = currencyList
    }


    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
        expanded = true
    }) {
        FlagItem(flagId = currency.flag)

        SpacerWidth(width = 8)
        if (isEditState) {

            Text(
                text = currency.shortCode,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        } else {
            Text(
                text = "$amount ${currency.shortCode}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }

        if (isEditState) {
            IconButton(
                modifier = Modifier.imePadding(),
                onClick = {
                    expanded = true
                }) {
                Icon(Icons.Default.ArrowDropDown, null, tint = MaterialTheme.colorScheme.outline)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    onSearchTextChanged("")
                },
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.tertiaryContainer)
                    .imePadding()


            ) {
                DropDownCurrencyList(
                    currencyList = currencyList,
                    onDropDownCurrencyClickListener = onCurrencyItemDropDownClickListener,
                    onSearchTextChanged = onSearchTextChanged,
                    onExpandedChange = { expanded = it }
                )
            }

        }
    }
}