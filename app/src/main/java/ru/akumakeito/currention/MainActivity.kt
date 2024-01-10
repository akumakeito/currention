package ru.akumakeito.currention

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.currention.dto.FiatCurrency
import ru.akumakeito.currention.ui.theme.CurrentionTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrentionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                }
            }

            CurrencyListScreen()
        }
    }
}


@Composable
fun CurrencyListScreen(viewModel: CurrencyViewModel = hiltViewModel()) {

    Column {
        Button(onClick = { viewModel.getFiatCurrencies() }) {
            Text(text = "Get Currencies")
        }
        CurrencyList(currencies = viewModel.fiatCurrencies.value)
    }

}

@Composable
fun CurrencyList(currencies: List<FiatCurrency>) {
    LazyColumn {
        items(currencies) { item ->
            CurrencyCard(item)
        }
    }

}

@Composable
fun CurrencyCard(
    currency: FiatCurrency
) {
    Card() {
        Row {
            Text(
                text = currency.code,
                textAlign = TextAlign.Start
            )
            Text(
                text = currency.name,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyListPreview() {
    CurrentionTheme {
        CurrencyListScreen()

        CurrencyList(
            listOf(
                FiatCurrency(1, "USD", "US Dollar", "$", "fbfu"),
                FiatCurrency(1, "USD", "US Dollar", "$", "fbfu"),
                FiatCurrency(1, "USD", "US Dollar", "$", "fbfu"),
                FiatCurrency(1, "USD", "US Dollar", "$", "fbfu"),
                FiatCurrency(1, "USD", "US Dollar", "$", "fbfu"),
                FiatCurrency(1, "USD", "US Dollar", "$", "fbfu"),
                FiatCurrency(1, "USD", "US Dollar", "$", "fbfu")
            )
        )
    }
}