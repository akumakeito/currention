package ru.akumakeito.currention.ui.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ru.akumakeito.currention.domain.FiatCurrency

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun DropDownCurrencyList(
    currencyList: List<FiatCurrency>,
    onDropDownCurrencyClickListener: (FiatCurrency) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onExpandedChange : (Boolean) -> Unit
) {
    var searchingText by remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    val state = rememberLazyListState()

    var expanded by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .width(130.dp)
            .height(300.dp)
            .background(color = MaterialTheme.colorScheme.tertiaryContainer)
    ) {

        LazyColumn(modifier = Modifier.weight(1f),
            state = state) {
            items(currencyList, key = { it.id }) { currency ->
                DropdownMenuItem(
                    text = { Text(text = currency.shortCode) },
                    onClick = {
                        onDropDownCurrencyClickListener(currency)
                        searchingText = TextFieldValue("")
                        expanded = false
                        onExpandedChange(expanded)
                        onSearchTextChanged("")
                    },
                    leadingIcon = {
                        FlagItem(flagId = currency.flag, 24)
                    },
                )
            }

        }
        HorizontalDivider()

        TextField(
            modifier = Modifier
                .focusRequester(focusRequester),
            value = searchingText,
            onValueChange = {
                searchingText = it
                onSearchTextChanged(searchingText.text)

            },
            prefix = {
                Icon(
                    Icons.Default.Search,
                    null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(0.dp)
                        .offset(x = (-4).dp),
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        searchingText = TextFieldValue("")
                        onSearchTextChanged("")
                    }

                ) {
                    Icon(
                        Icons.Default.Clear,
                        null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .size(16.dp)
                            .offset(x = 4.dp)
                    )
                }
            },

            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.tertiaryContainer,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledIndicatorColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            )
        )
    }
}
