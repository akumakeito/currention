package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.akumakeito.currention.CurrencyViewModel
import ru.akumakeito.currention.R
import ru.akumakeito.currention.domain.FiatCurrency

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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.currency_rates),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_update_rates),
                            contentDescription = stringResource(
                                R.string.update_rates
                            )
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {

                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.add_new_pair)
                )

            }
        }

    ) {

    }
}