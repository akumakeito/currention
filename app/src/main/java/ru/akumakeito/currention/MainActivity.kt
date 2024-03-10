package ru.akumakeito.currention

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.ui.items.CurrencyPairInExchangeRate
import ru.akumakeito.currention.ui.items.example
import ru.akumakeito.currention.ui.screens.ChooseFavoriteCurrencyScreen
import ru.akumakeito.currention.ui.screens.CurrencyExchangeRatesScreen
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
                    CurrencyPairInExchangeRate(pairCurrency = example, isEditing = false, onDeletePairClickListener = {}, onCurrencyDropDownClickListener = {})


                }
            }

        }
    }
}



@Composable
fun CurrencyListMain(currencies: List<FiatCurrency>) {
    LazyColumn {
        items(currencies) { item ->
            CurrencyCardTest(item)
        }
    }

}

@Composable
fun CurrencyCardTest(
    currency: FiatCurrency
) {

    val imageModifier = Modifier.size(40.dp)
    Card() {
        Row {
            Log.d("flag", currency.flag.toString())
            Image(
                modifier = imageModifier,
                painter = painterResource(id = currency.flag),
                contentDescription = currency.name
            )

            Text(
                text = currency.shortCode,
                textAlign = TextAlign.Start
            )
            Text(
                text = currency.name,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun CurrencyListPreview() {
    CurrentionTheme {

        CurrencyListMain(
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