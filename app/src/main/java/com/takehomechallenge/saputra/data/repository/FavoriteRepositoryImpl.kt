package com.takehomechallenge.saputra.data.repository

import com.takehomechallenge.saputra.data.db.FavoriteCharacterDao
import com.takehomechallenge.saputra.data.db.toCharacter
import com.takehomechallenge.saputra.data.db.toEntity
import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteCharacterDao: FavoriteCharacterDao
) : FavoriteRepository {

    private val scope = CoroutineScope(SupervisorJob())

    override fun getFavoriteCharacters(): StateFlow<List<Character>> {
        return favoriteCharacterDao.getAllFavorites()
            .map { entities -> entities.map { it.toCharacter() } }
            .stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    override suspend fun addFavorite(character: Character) {
        favoriteCharacterDao.insertFavorite(character.toEntity())
    }

    override suspend fun removeFavorite(characterId: Int) {
        favoriteCharacterDao.deleteFavorite(characterId)
    }

    override suspend fun isFavorite(characterId: Int): Boolean {
        return favoriteCharacterDao.isFavorite(characterId)
    }
}
