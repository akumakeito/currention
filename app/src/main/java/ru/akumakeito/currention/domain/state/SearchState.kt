package ru.akumakeito.currention.domain.state

data class SearchState(
    val isSearching: Boolean = false,
    val searchText: String = ""
)