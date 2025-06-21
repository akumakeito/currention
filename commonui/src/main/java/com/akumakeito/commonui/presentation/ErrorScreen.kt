package com.akumakeito.commonui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.akumakeito.commonres.R
import com.akumakeito.core.models.ErrorType
import com.akumakeito.core.util.log


@Composable
fun ErrorScreen(
    errorType: ErrorType
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        log("error type $errorType")
        Icon(
            painter = painterResource(R.drawable.ic_error),
            contentDescription = "error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .size(Dimens.IconSizeHuge)
                .padding(Dimens.x2)
        )

        Text(
            text = stringResource(errorType.message),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
