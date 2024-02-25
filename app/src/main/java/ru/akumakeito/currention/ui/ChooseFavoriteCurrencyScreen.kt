package ru.akumakeito.currention.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.akumakeito.currention.R
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.ui.theme.CurrentionTheme

val exp = FiatCurrency(
    1,
    "Доллар США",
    "USD",
    "1",
    "$",
    R.drawable.flag_usd
)

@Preview
@Composable
fun ChooseFavoriteCurrencyScreen() {
    CurrentionTheme {
        val lazyListState = rememberLazyListState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {

            SpacerHeight(height = 24)

            Text(
                text = stringResource(R.string.choose_currencies),
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            SpacerHeight(height = 24)
            /*TODO add segmented button*/

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {


                CurrencyListWithTitle(
                    lazyListState = lazyListState,
                    titleStringRes = R.string.popular,
                    currencyList = listOf(exp),
                    onCheckboxItemClickListener = {

                    }
                )

                SpacerHeight(height = 24)

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Text(
                        text = stringResource(R.string.next),
                        fontWeight = FontWeight.Bold
                    )
                }

                SpacerHeight(height = 24)
            }
        }

    }


}

@Composable
fun SpacerHeight(height: Int) {
    Spacer(modifier = Modifier.height(height.dp))
}

@Composable
fun SpacerWidth(width: Int) {
    Spacer(modifier = Modifier.width(width.dp))
}

@Composable
fun ColumnScope.CurrencyListWithTitle(
    lazyListState: LazyListState,
    titleStringRes: Int,
    currencyList: List<FiatCurrency>,
    modifier: Modifier = Modifier,
    onCheckboxItemClickListener: (FiatCurrency) -> Unit
) {

    Text(
        text = stringResource(titleStringRes),
        modifier = Modifier
            .fillMaxWidth(),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground
    )


    SpacerHeight(height = 24)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.height(100.dp)
    ) {
        items(currencyList) { item ->

            CurrencyCard(
                currency = item,
                onCheckboxClickListener = {
                    onCheckboxItemClickListener(item)
                }
            )

        }
    }
}


@Composable
fun CurrencyCard(
    currency: FiatCurrency,
    onCheckboxClickListener: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = currency.flag),
                contentDescription = stringResource(R.string.currency_name_flag, currency.name)
            )

            SpacerWidth(width = 16)

            Text(
                text = "${currency.name} (${currency.shortCode})",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )

            SpacerWidth(width = 16)

            Checkbox(checked = true, onCheckedChange = { onCheckboxClickListener })
        }
    }
}
