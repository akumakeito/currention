package ru.akumakeito.currention.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.hilt.navigation.compose.hiltViewModel
import ru.akumakeito.currention.CurrencyViewModel
import ru.akumakeito.currention.R
import ru.akumakeito.currention.ui.items.CurrencyPairInExchangeRate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyExchangeRatesScreen(
    paddingValues: PaddingValues,
    currencyViewModel: CurrencyViewModel = hiltViewModel()
) {

    val currencyPairs by currencyViewModel.currencyPairs.collectAsState(emptyList())
    val isEditing by currencyViewModel.isEditing.collectAsState()
    val editingPair by currencyViewModel.editPairCurrency.collectAsState()


    Column(Modifier.padding(paddingValues)) {
        LazyColumn(
        ) {
            itemsIndexed(currencyPairs) { index, item ->

    Log.d("editingPair", "item ${item}  \nedit ${editingPair}")

                CurrencyPairInExchangeRate(
                    pairCurrency = item,
                    onEditStateChange = {editingPair.id == item.id},
                    onDeletePairClickListener = {},
                    onCurrencyDropDownClickListener = {})
            }

        }
    }

    if (isEditing) {
        Button(
            onClick = { currencyViewModel.updatePair() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Text(
                text = stringResource(R.string.done),
                fontWeight = FontWeight.Bold
            )
        }

    }



}



