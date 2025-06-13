package com.akumakeito.commonui.presentation.items

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.akumakeito.commonui.presentation.Dimens


@Composable
fun NumberKeyboard(
    onNumberClick: (String) -> Unit,
    onDoneClick: () -> Unit,
    onBackspaceClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(Dimens.x2),
        horizontalArrangement = Arrangement.spacedBy(Dimens.x1)
    ) {
        Column(Modifier.weight(3f), verticalArrangement = Arrangement.spacedBy(Dimens.x1)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.x1)
            ) {
                NumberButton("1", onNumberClick, modifier = Modifier.weight(1f, true))
                NumberButton("2", onNumberClick, modifier = Modifier.weight(1f, true))
                NumberButton("3", onNumberClick, modifier = Modifier.weight(1f, true))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.x1)
            ) {
                NumberButton("4", onNumberClick, modifier = Modifier.weight(1f, true))
                NumberButton("5", onNumberClick, modifier = Modifier.weight(1f, true))
                NumberButton("6", onNumberClick, modifier = Modifier.weight(1f, true))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.x1)
            ) {
                NumberButton("7", onNumberClick, modifier = Modifier.weight(1f, true))
                NumberButton("8", onNumberClick, modifier = Modifier.weight(1f, true))
                NumberButton("9", onNumberClick, modifier = Modifier.weight(1f, true))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.x1)
            ) {
                NumberButton("0", onNumberClick, modifier = Modifier.weight(1f, true))
            }
        }

        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Dimens.x1)) {
            NumberButton(
                ".", onNumberClick, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), shape =
                    RoundedCornerShape(Dimens.x4)
            )
            IconButton(
                iconResId = com.akumakeito.commonres.R.drawable.ic_backspace,
                onClick = onBackspaceClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                tint = MaterialTheme.colorScheme.onSecondary,
                isEnabled = isEnabled,
            )
            IconButton(
                iconResId = com.akumakeito.commonres.R.drawable.ic_done,
                onClick = onDoneClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                tint = MaterialTheme.colorScheme.onPrimary,
                isEnabled = isEnabled,
            )
        }
    }
}


@Composable
private fun NumberButton(
    numberString: String,
    onNumberClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(Dimens.x3)
) {
    TextButton(
        onClick = { onNumberClick(numberString) },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier,
        shape = shape
    ) {
        Text(
            text = numberString,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun IconButton(
    @DrawableRes iconResId: Int,
    onClick: () -> Unit,
    color: ButtonColors = ButtonDefaults.buttonColors(),
    modifier: Modifier = Modifier,
    tint: Color,
    isEnabled: Boolean,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = color,
        shape = RoundedCornerShape(Dimens.x4),
        enabled = isEnabled
    ) {
        Icon(painter = painterResource(id = iconResId), contentDescription = null, tint = tint)
    }
}

@Composable
@Preview
fun KeyboardPreview() {
    NumberKeyboard(
        onNumberClick = {},
        onDoneClick = {},
        onBackspaceClick = {}
    )
}
