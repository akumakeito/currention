package com.akumakeito.commonui.presentation

data class StateModel(
    val isLoading: Boolean = false,
    val isError: ErrorType? = null
)

enum class ErrorType {
    NETWORK,
    SERVER
}