package com.akumakeito.commonui.presentation.items

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AmountTextField(
    value: String,
    readOnly: Boolean,
    onAmountTextChanged: (String) -> Unit,
    onClearAmount: () -> Unit,
    focusRequester: FocusRequester,
) {
    TextField(
        value = value,
        onValueChange = {
            onAmountTextChanged(it)
        },
        singleLine = true,
        modifier = Modifier
            .widthIn(1.dp, Dp.Infinity)
            .focusRequester(focusRequester),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.End,
            fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
            fontSize = 24.sp
        ),
        readOnly = readOnly,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            disabledIndicatorColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background,
        ),
        trailingIcon = {
            if (!readOnly) {
                IconButton(onClick = { onClearAmount() }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    )
}
