package ru.akumakeito.currention.ui.items

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.akumakeito.commonres.R
import com.akumakeito.commonui.presentation.Dimens
import com.akumakeito.commonui.presentation.navigation.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    screenTitle: Int,
    iconResId: Int? = null,
    onActionClick : () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier.padding(start = Dimens.x1, end = Dimens.x1),
        title = {
            Text(
                text = stringResource(id = screenTitle),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        actions = {
            if (iconResId != null) {
                IconButton(onClick = { onActionClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_update_rates),
                        contentDescription = "",
                        modifier = Modifier.size(Dimens.IconSize)
                    )
                }
            }
        }
    )
}