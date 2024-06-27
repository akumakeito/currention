package ru.akumakeito.currention.domain.state

data class StateModel(
    val isLoading: Boolean = false,
    val isError: ErrorType? = null
)

enum class ErrorType {
    NETWORK,
    SERVER
}