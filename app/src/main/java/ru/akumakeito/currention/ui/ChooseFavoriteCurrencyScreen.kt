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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.akumakeito.currention.CurrencyViewModel
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseFavoriteCurrencyScreen(
    currencyViewModel: CurrencyViewModel = hiltViewModel(),
    onCheckboxItemClickListener: (FiatCurrency) -> Unit,
) {

    val fiatCurrencyList = currencyViewModel.fiatCurrencies.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )

    val popularFiatCurrencyList =
        currencyViewModel.popularFiatCurrencies.collectAsStateWithLifecycle(
            initialValue = emptyList()
        )


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
                    .wrapContentHeight(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            SpacerHeight(height = 24)
            SegmentedButtonSingleSelect()
            SpacerHeight(height = 24)

            var text by rememberSaveable {
                mutableStateOf("")
            }
            var active by rememberSaveable {
                mutableStateOf(false)
            }

            Box(
                Modifier
                    .weight(1f)
                    .semantics { isTraversalGroup = true }) {

                DockedSearchBar(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp)
                        .semantics { traversalIndex = -1f },
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = { active = false },
                    active = active,
                    onActiveChange = { active = it },
                    placeholder = { Text(stringResource(R.string.enter_currency)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                ) {
                    repeat(4) { idx ->
                        val resultText = "Suggestion $idx"
                        ListItem(
                            headlineContent = { Text(resultText) },
                            supportingContent = { Text("Additional info") },
                            leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                            modifier = Modifier
                                .clickable {
                                    text = resultText
                                    active = false
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }

                LazyColumn(
                    contentPadding = PaddingValues(vertical = 16.dp),
                ) {

                    item {
                        HeaderMedium(header = R.string.popular)
                        SpacerHeight(height = 16)
                    }

                    items(items = popularFiatCurrencyList.value) { item ->
                        CurrencyCard(
                            currency = item,
                            onCheckboxClickListener = {
                                onCheckboxItemClickListener(item)
                            }
                        )

                    }

                    item {
                        SpacerHeight(height = 24)
                        HeaderMedium(header = R.string.all_currencies)
                        SpacerHeight(height = 16)


                    }

                    items(items = fiatCurrencyList.value) { item ->
                        CurrencyCard(
                            currency = item,
                            onCheckboxClickListener = {
                                onCheckboxItemClickListener(item)
                            }
                        )

                    }

                }
            }






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


@Composable
fun SpacerHeight(height: Int) {
    Spacer(modifier = Modifier.height(height.dp))
}

@Composable
fun SpacerWidth(width: Int) {
    Spacer(modifier = Modifier.width(width.dp))
}

@Composable
fun HeaderMedium(header: Int) {
    Text(
        text = stringResource(header),
        modifier = Modifier
            .fillMaxWidth(),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}


@Composable
fun CurrencyCard(
    modifier: Modifier = Modifier,
    currency: FiatCurrency,
    onCheckboxClickListener: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
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

            Checkbox(checked = false, onCheckedChange = { onCheckboxClickListener })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButtonSingleSelect(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableStateOf(0) }
    val options = listOf(R.string.fiat_currencies, R.string.crypto_currencies)
    SingleChoiceSegmentedButtonRow(modifier = modifier
        .fillMaxWidth()
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                selected = index == selectedIndex,
                onClick = { selectedIndex = index },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),

            ) {
                Text(text = stringResource(label), style = MaterialTheme.typography.labelLarge)
            }
        }

    }
}


