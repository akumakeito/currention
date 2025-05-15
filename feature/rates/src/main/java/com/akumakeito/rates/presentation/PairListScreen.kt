package ru.akumakeito.currention.ui.screens

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akumakeito.rates.R
import com.akumakeito.rates.presentation.PairCurrencyViewModel
import kotlinx.coroutines.launch


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PairListScreen(
//    paddingValues: PaddingValues,
//    pairViewModel: PairCurrencyViewModel = hiltViewModel(),
//) {
//    val scrollState = rememberScrollState()
//    val coroutineScope = rememberCoroutineScope()
//    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
//    val isEditing by pairViewModel.isEditing.collectAsState()
//
//
//    LaunchedEffect(key1 = keyboardHeight) {
//        coroutineScope.launch {
//            scrollState.scrollBy(keyboardHeight.toFloat())
//        }
//    }
//
//    Scaffold(
//        floatingActionButton = {
//            if (!isEditing) {
//                FloatingActionButton(
//                    modifier = Modifier
//                        .padding(16.dp),
//                    onClick = {
//                        pairViewModel.addNewCurrencyPair()
//                    },
//                    containerColor = MaterialTheme.colorScheme.primary
//                ) {
//
//                    Icon(
//                        imageVector = Icons.Rounded.Add,
//                        contentDescription = stringResource(R.string.add_new_pair)
//                    )
//
//                }
//            }
//        },
//    ) { innerPadding ->
//
//
//    }
//
//}



