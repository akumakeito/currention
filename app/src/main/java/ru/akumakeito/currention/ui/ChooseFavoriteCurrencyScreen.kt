package ru.akumakeito.currention.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import ru.akumakeito.currention.R
import ru.akumakeito.currention.dto.FiatCurrency
import ru.akumakeito.currention.ui.theme.CurrentionTheme

val exp = FiatCurrency(
    1,
    "Доллар США",
    "USD",
    "1",
    "$",
    R.drawable.flag_usd
)

@Preview(
    device = "id:pixel_6",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_UNDEFINED, name = "pixel6"
)
@Composable
fun ChooseFavoriteCurrencyScreen() {
    CurrentionTheme {
        val columnScrollState = rememberScrollState()

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


            CurrencyLazyColumn(columnScrollState = columnScrollState)

            SpacerHeight(height = 24)

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(text = stringResource(R.string.next),
                    fontWeight = FontWeight.Bold
                )
            }

            SpacerHeight(height = 24)
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
fun ColumnScope.CurrencyLazyColumn(
    columnScrollState: ScrollState,
) {
    Column(
        modifier = Modifier
            .verticalScroll(columnScrollState)
            .weight(1f)
    ) {
        Text(
            text = stringResource(R.string.popular),
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        SpacerHeight(height = 24)

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            repeat(7) {
                CurrencyCard(currency = exp)
            }
        }

        SpacerHeight(height = 48)

        Text(
            text = stringResource(R.string.all_currencies),
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        SpacerHeight(height = 24)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(500.dp)
        ) {
            items(20) {
                CurrencyCard(currency = exp)
            }
        }
    }
}


@Composable
fun CurrencyCard(currency: FiatCurrency) {
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

            Checkbox(checked = true, onCheckedChange = {})
        }
    }
}