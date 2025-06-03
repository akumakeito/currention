package com.akumakeito.commonui.presentation.items

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButtonSingleSelect(
    modifier: Modifier = Modifier,
    options: List<Int>,
    selectedIndex: Int = 0,
    onSelectionChanged: (Int) -> Unit,
    contents: Map<Int, @Composable () -> Unit>
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
            .fillMaxWidth()
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                selected = index == selectedIndex,
                onClick = {
                    onSelectionChanged(index)
                },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
            ) {
                Text(
                    text = stringResource(label),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
    SpacerHeight(height = 16)

    contents[selectedIndex]?.invoke()

}