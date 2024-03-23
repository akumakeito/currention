package ru.akumakeito.currention.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.akumakeito.currention.R

@Composable
fun FlagItem(flagId : Int, size : Int = 40) {
    Image(
        painter = painterResource(id = flagId),
        contentDescription = stringResource(R.string.flag),
        modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)
            .size(size.dp)
    )
}