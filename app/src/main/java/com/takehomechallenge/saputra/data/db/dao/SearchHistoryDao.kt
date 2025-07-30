package com.takehomechallenge.saputra.data.db.dao

import androidx.room.*
import com.takehomechallenge.saputra.data.db.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    
    @Query("SELECT * FROM search_history ORDER BY timestamp DESC")
    fun getAllHistories(): Flow<List<SearchHistoryEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: SearchHistoryEntity)
    
    @Query("DELETE FROM search_history WHERE keyword = :keyword")
    suspend fun deleteHistory(keyword: String)
    
    @Query("DELETE FROM search_history")
    suspend fun clearAllHistory()
    
    @Query("SELECT * FROM search_history WHERE keyword = :keyword LIMIT 1")
    suspend fun getHistoryByKeyword(keyword: String): SearchHistoryEntity?
}
