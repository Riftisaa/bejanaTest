package com.takehomechallenge.saputra.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    val favoriteCharacters: StateFlow<List<Character>> = favoriteRepository.getFavoriteCharacters()

    fun addFavorite(character: Character) {
        viewModelScope.launch {
            favoriteRepository.addFavorite(character)
        }
    }

    fun removeFavorite(character: Character) {
        viewModelScope.launch {
            favoriteRepository.removeFavorite(character.id)
        }
    }
}
