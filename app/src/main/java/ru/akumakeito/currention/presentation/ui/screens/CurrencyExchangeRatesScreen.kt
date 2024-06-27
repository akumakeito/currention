package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import ru.akumakeito.currention.R
import ru.akumakeito.currention.ui.items.CurrencyPairInExchangeRate
import ru.akumakeito.currention.viewmodel.PairCurrencyViewModel

@Composable
fun KeyboardAware(
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.imePadding()) {
        content()
    }
}

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun CurrencyExchangeRatesScreen(
    paddingValues: PaddingValues,
    pairViewModel: PairCurrencyViewModel,
    pullToRefreshState: PullToRefreshState,
    modifier: Modifier = Modifier
) {

    val currencyPairs by pairViewModel.currencyPairs.collectAsState()
    val editingPair by pairViewModel.editPairCurrency.collectAsState()
    val currencyList by pairViewModel.fiatCurrencies.collectAsState(emptyList())
    val state = rememberLazyListState()
    val uiState = pairViewModel.uiState.collectAsState()


    val pullRefreshState = rememberPullToRefreshState()


    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(true) {

            try {
                pairViewModel.updateAllPairsRates()
            } finally {
                pullRefreshState.endRefresh()

            }
        }
    }

    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .nestedScroll(pullRefreshState.nestedScrollConnection)
//        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (pullRefreshState.isRefreshing || uiState.value.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = state
        ) {
            items(currencyPairs, key = { it.id }) { item ->
                val delete = SwipeAction(
                    onSwipe = { pairViewModel.onSwipeToDelete(item) },
                    icon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(16.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.delete),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                    },
                    background = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                    isUndo = true
                )

                val edit = SwipeAction(
                    onSwipe = { pairViewModel.onSwipeToEdit(item) },
                    icon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                stringResource(id = R.string.edit),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Icon(
                                Icons.Default.Create,
                                contentDescription = stringResource(id = R.string.edit),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                    },
                    background = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    isUndo = true
                )

                SwipeableActionsBox(
                    swipeThreshold = 200.dp,
                    startActions = listOf(edit),
                    endActions = listOf(delete),
                    backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.outline
                ) {
                    CurrencyPairInExchangeRate(
                        pairCurrency = item,
                        onEditStateChange = {
//                            coroutineScope.launch {
//                                state.animateScrollToItem(index)
//                            }
                            editingPair.id == item.id
                        },
                        currencyList = currencyList,
                        onCurrencyFromDropDownClickListener = { selectedCurrency ->
                            pairViewModel.updatePairCurrencyFrom(selectedCurrency)
                        },
                        onCurrencyToDropDownClickListener = { selectedCurrency ->
                            pairViewModel.updatePairCurrencyTo(selectedCurrency)
                        },
                        editingPair = editingPair,
                        onSearchTextChanged = { },
                    )
                }


            }

        }


    }
}






