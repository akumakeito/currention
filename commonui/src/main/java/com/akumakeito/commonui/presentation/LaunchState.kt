package com.akumakeito.commonui.presentation

sealed class LaunchState {
    data object Starting : LaunchState()
    data object Main : LaunchState()
    data object OnBoarding : LaunchState()
    data object Error : LaunchState()
}