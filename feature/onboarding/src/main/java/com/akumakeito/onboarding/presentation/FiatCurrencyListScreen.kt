package com.akumakeito.onboarding.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonres.R
import com.akumakeito.commonui.presentation.Dimens
import com.akumakeito.commonui.presentation.items.CurrencyCardToChooseFavorite
import com.akumakeito.commonui.presentation.items.SpacerHeight
import com.akumakeito.convert.presentation.search.SearchState


@Composable
fun FiatCurrencyList(
    onboardingViewModel: OnboardingViewModel,
    searchingState: SearchState,
    fiatCurrencyList: List<FiatCurrency>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {

        OutlinedTextField(
            value = searchingState.searchText,
            onValueChange = onboardingViewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.enter_currency)) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                IconButton(onClick = { onboardingViewModel.onSearchTextChange("") }) {
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
            contentPadding = PaddingValues(vertical = Dimens.x2, horizontal = Dimens.x2),
        ) {

            items(items = fiatCurrencyList, key = { it.id }) { item ->
                CurrencyCardToChooseFavorite(
                    currency = item,
                    onCheckboxClickListener = {
                        onboardingViewModel.updateFavoriteCurrency(item)
                    },
                )
            }
        }
    }
}