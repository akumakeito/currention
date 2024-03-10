package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.akumakeito.currention.CurrencyViewModel
import ru.akumakeito.currention.R
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency
import ru.akumakeito.currention.ui.items.CurrencyPairInExchangeRate
import ru.akumakeito.currention.ui.items.example

val usd = FiatCurrency(
    1,
    "Доллар США",
    "USD",
    "840",
    "$",
    R.drawable.flag_usd
)

val rub = FiatCurrency(
    1,
    "Российский рубль",
    "RUB",
    "643",
    "P",
    R.drawable.flag_rub
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyExchangeRatesScreen(
    paddingValues: PaddingValues,
    currencyViewModel: CurrencyViewModel = hiltViewModel()
) {

        val pairList = listOf(
            example,
            example, example

        )

        Box(Modifier.padding(paddingValues)) {
            LazyColumn(
            ) {
                items(pairList) { item ->
                    CurrencyPairInExchangeRate(
                        pairCurrency = item,
                        isEditing = false,
                        onDeletePairClickListener = {},
                        onCurrencyDropDownClickListener = {})
                }

            }
        }


    }
