package com.akumakeito.convert.presentation.search

data class SearchState(
    val isSearching: Boolean = false,
    val searchText: String = ""
)