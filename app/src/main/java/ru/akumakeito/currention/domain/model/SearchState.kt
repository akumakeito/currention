package ru.akumakeito.currention.domain.model

data class SearchState(
    val isSearching: Boolean = false,
    val searchText: String = ""
)