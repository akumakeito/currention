package com.akumakeito.convert.presentation.convert

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akumakeito.commonres.R
import com.akumakeito.commonui.presentation.Dimens
import com.akumakeito.commonui.presentation.items.CurrencyFlagAmountShortCode
import com.akumakeito.commonui.presentation.items.NumberKeyboard
import com.akumakeito.convert.presentation.convert.items.FlagShortCodeAmount
import com.akumakeito.core.util.log

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun FavCurrencyConverterScreen(
    viewModel: FavCurrencyConverterViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    var amountState by rememberSaveable {
        mutableStateOf(state.amount)
    }

    LaunchedEffect(key1 = state.amount) {
        amountState = state.amount
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = Dimens.x4, vertical = Dimens.x2)
            ) {
                CurrencyFlagAmountShortCode(
                    fiatCurrency = state.from,
                    amount = "",
                    isEditing = true,
                    onCurrencyItemDropDownClickListener = { viewModel.selectBaseCurrency(it) },
                    currencyList = state.favCurrency,
                    onSearchTextChanged = {},
                    textColor = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = amountState,
                    modifier = Modifier.padding(end = Dimens.x1),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp
                )
                IconButton(onClick = { viewModel.onClearClick() }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier,
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.secondaryContainer
            )

            val error = state.error
            log("error $error")

            if (error != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.error.copy(0.8f))
                        .padding(horizontal = Dimens.x2),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(error.message),
                        color = MaterialTheme.colorScheme.onError,
                        style = MaterialTheme.typography.bodySmall
                    )

                    IconButton(onClick = { viewModel.onCloseError() }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onError,
                        )
                    }
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                    .padding(Dimens.x2),
                text = stringResource(R.string.last_update, state.lastUpdate),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodySmall
            )

            Column(
                modifier = Modifier
                    .padding(
                        start = Dimens.x4,
                        top = Dimens.x1,
                        end = Dimens.x5,
                        bottom = Dimens.x1
                    )
                    .verticalScroll(scrollState)
            ) {
                for (item in state.convertedToFavorites) {
                    FlagShortCodeAmount(
                        isLoading = state.isLoading,
                        currency = item
                    )
                }
            }
        }

        NumberKeyboard(
            onNumberClick = { amount ->
                viewModel.onAmountChanged(amount)
            },
            onDoneClick = { viewModel.convert() },
            onBackspaceClick = { viewModel.onBackspaceClick() },
            modifier = Modifier,
            isEnabled = state.isButtonEnable
        )
    }
}
