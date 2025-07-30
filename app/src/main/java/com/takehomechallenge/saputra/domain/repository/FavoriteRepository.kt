package com.takehomechallenge.saputra.domain.repository

import com.takehomechallenge.saputra.data.model.Character
import kotlinx.coroutines.flow.StateFlow

interface FavoriteRepository {
    fun getFavoriteCharacters(): StateFlow<List<Character>>
    suspend fun addFavorite(character: Character)
    suspend fun removeFavorite(characterId: Int)
    suspend fun isFavorite(characterId: Int): Boolean
}
