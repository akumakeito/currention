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
import androidx.compose.ui.unit.dp
import ru.akumakeito.currention.R
import ru.akumakeito.currention.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    currentScreen: Screen,
    isUpdatable: Boolean = false
) {
    TopAppBar(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        title = {
            Text(
                text = stringResource(id = currentScreen.titleResId),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        actions = {
            if (isUpdatable) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_update_rates),
                        contentDescription = stringResource(
                            R.string.update_rates
                        ),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    )
}