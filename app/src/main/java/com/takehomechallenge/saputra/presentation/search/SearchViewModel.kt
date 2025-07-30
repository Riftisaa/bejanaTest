package com.takehomechallenge.saputra.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takehomechallenge.saputra.data.api.RickAndMortyApi
import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.data.repository.SearchHistoryRepository
import com.takehomechallenge.saputra.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SearchViewModel @Inject constructor(
    private val api: RickAndMortyApi,
    private val searchHistoryRepository: SearchHistoryRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    // Expose favorite characters StateFlow for real-time UI updates
    val favoriteCharacters: StateFlow<List<Character>> = favoriteRepository.getFavoriteCharacters()

    init {
        loadSearchHistories()
    }

    open fun toggleFavorite(character: Character) {
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

    private fun loadSearchHistories() {
        viewModelScope.launch {
            searchHistoryRepository.getAllHistories().collect { histories ->
                val currentState = _uiState.value
                when (currentState) {
                    is SearchUiState.Success -> {
                        _uiState.value = currentState.copy(searchHistories = histories)
                    }
                    else -> {
                        _uiState.value = SearchUiState.Success(
                            characters = emptyList(),
                            searchHistories = histories
                        )
                    }
                }
            }
        }
    }

    fun searchCharacters(query: String) {
        if (query.isBlank()) {
            _uiState.value = SearchUiState.Success(
                characters = emptyList(),
                searchHistories = (_uiState.value as? SearchUiState.Success)?.searchHistories ?: emptyList(),
                query = query
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            try {
                val response = api.getCharacters(name = query)
                // Add to search history
                searchHistoryRepository.insertSearchHistory(query)
                
                _uiState.value = SearchUiState.Success(
                    characters = response.results,
                    searchHistories = (_uiState.value as? SearchUiState.Success)?.searchHistories ?: emptyList(),
                    query = query
                )
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(
                    message = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun onSearchBarFocusChanged(focused: Boolean) {
        val currentState = _uiState.value
        if (currentState is SearchUiState.Success) {
            _uiState.value = currentState.copy(isSearchBarFocused = focused)
        }
    }

    fun deleteSearchHistory(keyword: String) {
        viewModelScope.launch {
            searchHistoryRepository.deleteSearchHistory(keyword)
        }
    }

    fun clearAllSearchHistory() {
        viewModelScope.launch {
            searchHistoryRepository.clearAllHistory()
        }
    }
}
