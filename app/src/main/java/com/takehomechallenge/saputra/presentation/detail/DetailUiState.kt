package com.takehomechallenge.saputra.presentation.detail

import com.takehomechallenge.saputra.data.model.Character

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Error(val message: String) : DetailUiState
    data class Success(val character: Character) : DetailUiState
}
