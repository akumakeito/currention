package ru.akumakeito.currention.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akumakeito.commonres.R
import com.akumakeito.commonui.presentation.Dimens
import com.akumakeito.commonui.presentation.items.SegmentedButtonSingleSelect
import com.akumakeito.commonui.presentation.items.SpacerHeight
import com.akumakeito.onboarding.presentation.CryptoCurrencyListScreen
import com.akumakeito.onboarding.presentation.FiatCurrencyList
import com.akumakeito.onboarding.presentation.OnboardingViewModel
import kotlinx.coroutines.launch

@Composable
fun ChooseFavoriteCurrencyScreen(
    onDoneClick: () -> Unit
) {
    val onboardingViewModel: OnboardingViewModel = hiltViewModel()
    val state by onboardingViewModel.state.collectAsStateWithLifecycle()

    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf(R.string.fiat_currencies, R.string.crypto_currencies)

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showFAB by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0
        }
    }
    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = showFAB,
                enter = fadeIn(),
                exit = fadeOut()
            ) {

                SmallFloatingActionButton(
                    onClick = { scope.launch { lazyListState.animateScrollToItem(0) } },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "up",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        bottomBar = {
            Button(
                enabled = state.isButtonEnable,
                onClick = {
                    onboardingViewModel.onDoneClick()
                    onDoneClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.x2)

            ) {
                Text(
                    text = stringResource(R.string.next),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimens.x3)
            ) {

                SpacerHeight(height = 24)

                Text(
                    text = stringResource(R.string.choose_currencies),
                    modifier = Modifier
                        .wrapContentHeight(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.SemiBold
                )

                SpacerHeight(height = 16)
                SegmentedButtonSingleSelect(
                    options = options,
                    selectedIndex = selectedIndex,
                    onSelectionChanged = {
                        selectedIndex = it
                    },
                    contents = mapOf(
                        0 to {
                            FiatCurrencyList(
                                onboardingViewModel = onboardingViewModel,
                                searchingState = state.searchState,
                                fiatCurrencyList = state.fiatCurrencyList,
                                lazyListState = lazyListState
                            )
                        },
                        1 to { CryptoCurrencyListScreen() }
                    )
                )
            }
        }
    }
}
