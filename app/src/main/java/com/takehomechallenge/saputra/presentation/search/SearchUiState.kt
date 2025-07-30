package com.takehomechallenge.saputra.presentation.search

import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.data.db.SearchHistoryEntity

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Error(val message: String) : SearchUiState
    data class Success(
        val characters: List<Character>,
        val searchHistories: List<SearchHistoryEntity> = emptyList(),
        val query: String = "",
        val isSearchBarFocused: Boolean = false
    ) : SearchUiState
}
