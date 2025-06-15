package com.akumakeito.parameters.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akumakeito.commonres.R
import com.akumakeito.commonui.presentation.Dimens

@Composable
fun SettingsItem(
    painter: Painter,
    content: String,
    hasSwitch: Boolean,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    swiched: Boolean = false
) {

    Row(
        modifier = modifier
            .clickable { onItemClick() }
            .padding(vertical = if (hasSwitch) 0.dp else Dimens.x1_5),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painter,
            contentDescription = content,
            modifier = Modifier.padding(end = Dimens.x2),
            tint = MaterialTheme.colorScheme.outline
        )

        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        if (hasSwitch) {
            Switch(checked = swiched, onCheckedChange = { onItemClick() })
        }
    }
}

@Preview
@Composable
fun SettingsItemPreview() {
    SettingsItem(
        painter = painterResource(id = R.drawable.ic_switch_theme),
        content = "Темная тема",
        hasSwitch = true,
        onItemClick = {})

}