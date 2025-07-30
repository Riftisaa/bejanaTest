package com.takehomechallenge.saputra.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {
    
    @Query("SELECT * FROM favorite_characters ORDER BY timestamp DESC")
    fun getAllFavorites(): Flow<List<FavoriteCharacterEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(character: FavoriteCharacterEntity)
    
    @Query("DELETE FROM favorite_characters WHERE id = :characterId")
    suspend fun deleteFavorite(characterId: Int)
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE id = :characterId)")
    suspend fun isFavorite(characterId: Int): Boolean
    
    @Query("SELECT COUNT(*) FROM favorite_characters")
    suspend fun getFavoriteCount(): Int
    
    @Query("DELETE FROM favorite_characters")
    suspend fun clearAllFavorites()
}
