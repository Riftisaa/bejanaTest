package com.takehomechallenge.saputra.data.repository

import com.takehomechallenge.saputra.data.db.FavoriteCharacterDao
import com.takehomechallenge.saputra.data.db.FavoriteCharacterEntity
import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.data.model.Location
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import kotlinx.coroutines.flow.first
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.mockito.kotlin.any

/**
 * Unit tests for FavoriteRepositoryImpl
 * Tests favorite character operations with SQLite database
 */
class FavoriteRepositoryImplTest {

    @Mock
    private lateinit var favoriteCharacterDao: FavoriteCharacterDao

    private lateinit var repository: FavoriteRepositoryImpl

    private val testCharacter = Character(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = Location("Earth", ""),
        location = Location("Earth", ""),
        image = "test.jpg",
        episode = listOf("S01E01"),
        url = "",
        created = ""
    )

    private val testEntity = FavoriteCharacterEntity(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        originName = "Earth",
        originUrl = "",
        locationName = "Earth",
        locationUrl = "",
        image = "test.jpg",
        episodes = "S01E01",
        url = "",
        created = ""
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = FavoriteRepositoryImpl(favoriteCharacterDao)
    }

    @Test
    fun `addFavorite should call dao insertFavorite`() = runTest {
        // When
        repository.addFavorite(testCharacter)

        // Then
        verify(favoriteCharacterDao).insertFavorite(any())
    }

    @Test
    fun `removeFavorite should call dao deleteFavorite with correct id`() = runTest {
        // When
        repository.removeFavorite(1)

        // Then
        verify(favoriteCharacterDao).deleteFavorite(1)
    }

    @Test
    fun `isFavorite should return correct boolean value`() = runTest {
        // Given
        whenever(favoriteCharacterDao.isFavorite(1)).thenReturn(true)
        whenever(favoriteCharacterDao.isFavorite(2)).thenReturn(false)

        // When & Then
        assertTrue(repository.isFavorite(1))
        assertFalse(repository.isFavorite(2))
    }
}
