package com.akumakeito.commonui.presentation

data class StateModel(
    val isLoading: Boolean = false,
    val isError: ErrorType? = null
)

sealed class ResultState {
    data object Loading : ResultState()
    data object Success : ResultState()
    data class Error(val errorType: ErrorType) : ResultState()
}

sealed class LaunchState {
    data object Starting : LaunchState()
    data object Main : LaunchState()
    data object OnBoarding : LaunchState()
}

enum class ErrorType {
    NETWORK,
    SERVER
}