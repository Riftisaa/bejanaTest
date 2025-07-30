package com.takehomechallenge.saputra.presentation.home

import com.takehomechallenge.saputra.data.api.RickAndMortyApi
import com.takehomechallenge.saputra.data.model.ApiResponse
import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.data.model.Info
import com.takehomechallenge.saputra.data.model.Location
import com.takehomechallenge.saputra.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

/**
 * Unit tests for HomeViewModel
 * Tests character loading and favorite toggle functionality
 */
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @Mock
    private lateinit var api: RickAndMortyApi

    @Mock
    private lateinit var favoriteRepository: FavoriteRepository

    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

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

    private val testResponse = ApiResponse(
        info = Info(1, 1, "", ""),
        results = listOf(testCharacter)
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        
        whenever(favoriteRepository.getFavoriteCharacters()).thenReturn(MutableStateFlow(emptyList()))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `viewModel should load characters successfully`() = runTest {
        // Given
        whenever(api.getCharacters()).thenReturn(testResponse)

        // When
        viewModel = HomeViewModel(api, favoriteRepository)

        // Then
        val uiState = viewModel.uiState.value
        assertTrue(uiState is HomeUiState.Success)
        assertEquals(1, (uiState as HomeUiState.Success).characters.size)
        assertEquals("Rick Sanchez", uiState.characters[0].name)
    }

    @Test
    fun `viewModel should handle API error correctly`() = runTest {
        // Given
        whenever(api.getCharacters()).thenThrow(RuntimeException("Network error"))

        // When
        viewModel = HomeViewModel(api, favoriteRepository)

        // Then
        val uiState = viewModel.uiState.value
        assertTrue(uiState is HomeUiState.Error)
        assertEquals("Network error", (uiState as HomeUiState.Error).message)
    }

    @Test
    fun `isFavorite should return correct value from repository`() = runTest {
        // Given
        whenever(api.getCharacters()).thenReturn(testResponse)
        whenever(favoriteRepository.getFavoriteCharacters()).thenReturn(MutableStateFlow(listOf(testCharacter)))

        // When
        viewModel = HomeViewModel(api, favoriteRepository)

        // Then
        assertTrue(viewModel.isFavorite(1))
        assertFalse(viewModel.isFavorite(2))
    }
}
