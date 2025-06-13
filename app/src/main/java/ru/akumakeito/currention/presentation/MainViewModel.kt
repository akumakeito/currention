package ru.akumakeito.currention.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akumakeito.commonui.presentation.LaunchState
import com.akumakeito.commonui.presentation.ResultState
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.core.appsettings.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val launchState: SharedFlow<LaunchState> = combine(
        appSettingsRepository.getFirstStart(),
        currencyRepository.downloadInitialFiatCurrencyList()
    ) { isFirstStart, downloadResult ->
        when (downloadResult) {
            is ResultState.Error -> LaunchState.Starting
            ResultState.Loading -> LaunchState.Starting
            ResultState.Success -> if (isFirstStart) LaunchState.OnBoarding else LaunchState.Main
        }
    }
        .shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
        )
}