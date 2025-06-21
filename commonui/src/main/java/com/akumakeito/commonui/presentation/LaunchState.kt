package com.akumakeito.commonui.presentation

import com.akumakeito.core.models.ErrorType

sealed class LaunchState {
    data object Starting : LaunchState()
    data object Main : LaunchState()
    data object OnBoarding : LaunchState()
    data class Error(val errorType : ErrorType) : LaunchState()
}