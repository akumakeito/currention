package com.akumakeito.commonui.presentation

data class StateModel(
    val isLoading: Boolean = false,
    val isSuccess : Boolean? = null,
    val isError: ErrorType? = null
)

sealed class LaunchState {
    data object Starting : LaunchState()
    data object Main : LaunchState()
    data object OnBoarding : LaunchState()
    data object Error : LaunchState()
}

enum class ErrorType {
    NETWORK,
    SERVER
}