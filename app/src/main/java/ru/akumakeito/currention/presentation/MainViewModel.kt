package ru.akumakeito.currention.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akumakeito.commonui.presentation.LaunchState
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.core.appsettings.AppSettingsRepository
import com.akumakeito.core.network.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val currencyRepository: CurrencyRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>(replay = 0)

    init {
        observeNetwork()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val launchState: StateFlow<LaunchState> = combine(
        appSettingsRepository.getFirstStart(),
        retryTrigger
            .onStart { emit(Unit) }
            .flatMapLatest {
                currencyRepository.downloadInitialFiatCurrencyList()
            }
    ) { isFirstStart, downloadResult ->
        when {
            downloadResult.isSuccess && isFirstStart -> LaunchState.OnBoarding
            downloadResult.isSuccess -> LaunchState.Main
            else -> LaunchState.Error
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LaunchState.Starting
        )

    private val isDarkTheme = appSettingsRepository.isDarkTheme.shareIn(
        viewModelScope,
        SharingStarted.Eagerly,
    )

    private val isSystemTheme = appSettingsRepository.isSystemTheme.shareIn(
        viewModelScope,
        SharingStarted.Eagerly,
    )

    val isDark = combine(
        isDarkTheme,
        isSystemTheme
    ) { isDarkTheme, isSystemTheme ->
        isDarkTheme || (isSystemTheme && isDarkTheme)
    }.shareIn(
        viewModelScope,
        SharingStarted.Eagerly,
    )

    private fun observeNetwork() {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { connected ->
                if (connected && launchState.value == LaunchState.Error) {
                    retryTrigger.emit(Unit)
                }
            }
        }
    }
}