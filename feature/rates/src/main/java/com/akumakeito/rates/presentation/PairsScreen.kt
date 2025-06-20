package ru.akumakeito.currention.ui.screens

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akumakeito.commonres.R
import com.akumakeito.commonui.presentation.Dimens
import com.akumakeito.rates.presentation.PairCurrencyViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import ru.akumakeito.currention.ui.items.CurrencyPairInExchangeRate
import ru.akumakeito.currention.ui.items.CustomTopAppBar
import com.akumakeito.commonres.R as CommonRes


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PairsScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val pairViewModel: PairCurrencyViewModel = hiltViewModel()
    val scrollState = rememberLazyListState()
    val state by pairViewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.padding(paddingValues),
        topBar = {
            CustomTopAppBar(
                screenTitle = R.string.currency_rates,
                iconResId = CommonRes.drawable.ic_update_rates,
                onActionClick = {
                    pairViewModel.updateAllPairsRates()
                }
            )
        },
        floatingActionButton = {
            if (!state.isEditing) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(Dimens.x2),
                    onClick = {
                        pairViewModel.addNewCurrencyPair()
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "add new pair"
                    )
                }
            }
        },
        bottomBar = {
            if (state.isEditing) {
                Button(
                    onClick = { pairViewModel.updatePair() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(Dimens.x2),
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(Dimens.IconSizeSmall),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round,
                            strokeWidth = 1.dp
                        )
                    } else {
                        Text(
                            text = stringResource(CommonRes.string.done),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = scrollState
        ) {
            items(items = state.pairs, key = { it.id }) { item ->
                val delete = SwipeAction(
                    onSwipe = { pairViewModel.onSwipeToDelete(item) },
                    icon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = stringResource(id = CommonRes.string.delete),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(Dimens.x2)
                            )
                            Text(
                                text = stringResource(id = CommonRes.string.delete),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                    },
                    background = MaterialTheme.colorScheme.error,
                    isUndo = true
                )

                val edit = SwipeAction(
                    onSwipe = { pairViewModel.onSwipeToEdit(item) },
                    icon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                stringResource(id = CommonRes.string.edit),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Icon(
                                Icons.Default.Create,
                                contentDescription = stringResource(id = CommonRes.string.edit),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(Dimens.x2)
                            )
                        }

                    },
                    background = MaterialTheme.colorScheme.primary,
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
                            state.editingPair.id == item.id
                        },
                        currencyList = state.favoriteCurrencies,
                        onCurrencyFromDropDownClickListener = { selectedCurrency ->
                            pairViewModel.updatePairCurrencyFrom(selectedCurrency)
                        },
                        onCurrencyToDropDownClickListener = { selectedCurrency ->
                            pairViewModel.updatePairCurrencyTo(selectedCurrency)
                        },
                        editingPair = state.editingPair,
                    )
                }
            }

        }
    }
}







