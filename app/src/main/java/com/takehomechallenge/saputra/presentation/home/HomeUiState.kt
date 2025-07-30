package com.takehomechallenge.saputra.presentation.home

import com.takehomechallenge.saputra.data.model.Character

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
    data class Success(
        val characters: List<Character>
    ) : HomeUiState
} 