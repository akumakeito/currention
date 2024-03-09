package ru.akumakeito.currention.ui.screens

import SegmentedButtonSingleSelect
import SpacerHeight
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.akumakeito.currention.CurrencyViewModel
import ru.akumakeito.currention.R
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.ui.items.CurrencyCardToChooseFavorite
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
fun ChooseFavoriteCurrencyScreen(
    currencyViewModel: CurrencyViewModel = hiltViewModel(),

    ) {
    val fiatCurrencyList by currencyViewModel.fiatCurrencies.collectAsState(emptyList())
    val searchingState by currencyViewModel.searchingState.collectAsState()
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showFAB by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0
        }
    }
    CurrentionTheme {


        Scaffold(
            floatingActionButton = {

                AnimatedVisibility(
                    visible = showFAB,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {

                    SmallFloatingActionButton(
                        onClick = { scope.launch { lazyListState.animateScrollToItem(0) } },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "up",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

            },
            bottomBar = {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)

                ) {
                    Text(
                        text = stringResource(R.string.next),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        ) { paddingValues ->
            Box(Modifier.padding(paddingValues)) {

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
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.SemiBold
                    )

                    SpacerHeight(height = 16)
                    SegmentedButtonSingleSelect()
                    SpacerHeight(height = 16)

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        OutlinedTextField(
                            value = searchingState.searchText,
                            onValueChange = currencyViewModel::onSearchTextChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(stringResource(R.string.enter_currency)) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { currencyViewModel.onSearchTextChange("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                                }
                            },
                            shape = RoundedCornerShape(50),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                focusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
                                unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
                                focusedLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                focusedTrailingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                focusedPlaceholderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            ),
                        )

                        SpacerHeight(height = 16)

                        LazyColumn(
                            state = lazyListState,
                            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp),
                        ) {

                            items(items = fiatCurrencyList, key = { it.id }) { item ->
                                CurrencyCardToChooseFavorite(
                                    currency = item,
                                    onCheckboxClickListener = {
                                        Log.d("checkbox", "onCheckboxClickListener $item")
                                        currencyViewModel.updateFavoriteCurrency(item)
                                    },

                                    )

                            }
                        }


                    }


                }
            }
        }
    }
}








