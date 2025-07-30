package com.takehomechallenge.saputra.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takehomechallenge.saputra.data.api.RickAndMortyApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val api: RickAndMortyApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val character = api.getCharacter(characterId)
                _uiState.value = DetailUiState.Success(character = character)
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(
                    message = e.message ?: "Failed to load character details"
                )
            }
        }
    }
}
