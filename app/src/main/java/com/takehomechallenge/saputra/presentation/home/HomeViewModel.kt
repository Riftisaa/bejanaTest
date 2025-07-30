package com.takehomechallenge.saputra.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takehomechallenge.saputra.data.api.RickAndMortyApi
import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor(
    private val api: RickAndMortyApi,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    protected val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // Expose favorite characters StateFlow for real-time UI updates
    val favoriteCharacters: StateFlow<List<Character>> = favoriteRepository.getFavoriteCharacters()

    init {
        loadCharacters()
    }

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            if (favoriteRepository.isFavorite(character.id)) {
                favoriteRepository.removeFavorite(character.id)
            } else {
                favoriteRepository.addFavorite(character)
            }
        }
    }

    fun isFavorite(characterId: Int): Boolean {
        return favoriteCharacters.value.any { it.id == characterId }
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            try {
                val response = api.getCharacters()
                _uiState.value = HomeUiState.Success(
                    characters = response.results
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(
                    message = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
} 