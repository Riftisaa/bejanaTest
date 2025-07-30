package com.takehomechallenge.saputra.data.repository

import com.takehomechallenge.saputra.data.db.SearchHistoryDao
import com.takehomechallenge.saputra.data.db.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchHistoryRepository @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
) {
    
    fun getAllHistories(): Flow<List<SearchHistoryEntity>> {
        return searchHistoryDao.getAllHistories()
    }
    
    suspend fun insertSearchHistory(keyword: String) {
        val searchHistory = SearchHistoryEntity(
            keyword = keyword,
            timestamp = System.currentTimeMillis()
        )
        searchHistoryDao.insert(searchHistory)
    }
    
    suspend fun deleteSearchHistory(keyword: String) {
        searchHistoryDao.delete(keyword)
    }
    
    suspend fun clearAllHistory() {
        searchHistoryDao.clearAll()
    }
}
