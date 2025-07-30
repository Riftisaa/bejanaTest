package com.takehomechallenge.saputra.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
//import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.takehomechallenge.saputra.R
import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.data.model.Location
import com.takehomechallenge.saputra.data.db.SearchHistoryEntity
import com.takehomechallenge.saputra.presentation.common.SearchBar
import com.takehomechallenge.saputra.presentation.home.components.CharacterItem
import com.takehomechallenge.saputra.ui.theme.BejanaTestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    initialQuery: String = "",
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteCharacters by viewModel.favoriteCharacters.collectAsStateWithLifecycle()
    
    // Search with initial query if provided
    LaunchedEffect(initialQuery) {
        if (initialQuery.isNotEmpty()) {
            viewModel.searchCharacters(initialQuery)
        }
    }
    
    SearchScreenContent(
        uiState = uiState,
        favoriteCharacters = favoriteCharacters,
        initialQuery = initialQuery,
        onNavigateBack = onNavigateBack,
        onNavigateToDetail = onNavigateToDetail,
        onSearchCharacters = viewModel::searchCharacters,
        onSearchBarFocusChanged = viewModel::onSearchBarFocusChanged,
        onDeleteSearchHistory = viewModel::deleteSearchHistory,
        onClearAllHistory = viewModel::clearAllSearchHistory,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
    uiState: SearchUiState,
    favoriteCharacters: List<Character>,
    initialQuery: String,
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onSearchCharacters: (String) -> Unit,
    onSearchBarFocusChanged: (Boolean) -> Unit,
    onDeleteSearchHistory: (String) -> Unit,
    onClearAllHistory: () -> Unit,
    viewModel: SearchViewModel
) {
    var searchQuery by remember { mutableStateOf(initialQuery) }
    
    // Determine background opacity based on search state
    val hasSearchResults = uiState is SearchUiState.Success && uiState.characters.isNotEmpty()
    val isSearchFocused = uiState is SearchUiState.Success && uiState.isSearchBarFocused
    val isShowingHistory = (uiState is SearchUiState.Success && uiState.isSearchBarFocused) || 
                          (uiState is SearchUiState.Success && uiState.characters.isEmpty() && uiState.query.isEmpty())
    
    // Background opacity: 20% when focused/typing/showing history, 100% when showing results
    val backgroundAlpha = if (hasSearchResults) 1f else 0.2f
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.primary_background),
                contentScale = ContentScale.FillBounds,
                alpha = backgroundAlpha
            )
    ) {
        // White overlay for content when background is dimmed
        if (!hasSearchResults) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.8f))
            )

        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            // Header with back button and logo - only show when has search results
            if (hasSearchResults) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF87F54E)
                        )
                    }

                    Text(
                        text = "GO BACK",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF87F54E),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }



            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_1),
                contentDescription = "Logo 1",
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            
            // Search bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { query ->
                    searchQuery = query
                },
                onFocusChanged = { focused ->
                    onSearchBarFocusChanged(focused)
                },
                onSearchSubmit = { query ->
                    if (query.isNotEmpty()) {
                        onSearchCharacters(query)
                        onSearchBarFocusChanged(false)
                    }
                },
                backgroundColor = Color(0xFF87F54E),
                placeholder = "Filter by name..."
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Content based on UI state and focus
            when {
                // Show search history when search bar is focused and no search results
                uiState is SearchUiState.Success && uiState.isSearchBarFocused -> {
                    SearchHistoryContent(
                        searchHistories = uiState.searchHistories,
                        onHistoryItemClick = { keyword ->
                            searchQuery = keyword
                            onSearchCharacters(keyword)
                            onSearchBarFocusChanged(false)
                        },
                        onDeleteHistory = onDeleteSearchHistory,
                        onClearAllHistory = onClearAllHistory
                    )
                }
                
                uiState is SearchUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF87F54E)
                        )
                    }
                }
                
                uiState is SearchUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.message,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    }
                }
                
                uiState is SearchUiState.Success && uiState.characters.isNotEmpty() -> {
                    // Search results
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.characters) { character ->
                            CharacterItem(
                                character = character,
                                onCharacterClick = onNavigateToDetail,
                                onFavoriteClick = { character ->
                                    viewModel.toggleFavorite(character)
                                },
                                isFavorite = favoriteCharacters.any { it.id == character.id }
                            )
                        }
                    }
                }
                
                uiState is SearchUiState.Success && uiState.query.isNotEmpty() && uiState.characters.isEmpty() -> {
                    // No results found
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No characters found for \"${uiState.query}\"",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                }
                
                else -> {
                    // Initial state - show search history
                    SearchHistoryContent(
                        searchHistories = (uiState as? SearchUiState.Success)?.searchHistories ?: emptyList(),
                        onHistoryItemClick = { keyword ->
                            searchQuery = keyword
                            onSearchCharacters(keyword)
                        },
                        onDeleteHistory = onDeleteSearchHistory,
                        onClearAllHistory = onClearAllHistory
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchHistoryContent(
    searchHistories: List<SearchHistoryEntity>,
    onHistoryItemClick: (String) -> Unit,
    onDeleteHistory: (String) -> Unit,
    onClearAllHistory: () -> Unit
) {
    Column {
        if (searchHistories.isNotEmpty()) {
            Text(
                text = "Search History",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
            
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(searchHistories) { history ->
                    SearchHistoryItem(
                        history = history,
                        onItemClick = { onHistoryItemClick(history.keyword) },
                        onDeleteClick = { onDeleteHistory(history.keyword) }
                    )
                }
            }
            
            TextButton(
                onClick = onClearAllHistory,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Delete all",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No search history yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun SearchHistoryItem(
    history: SearchHistoryEntity,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onItemClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F8)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Icon(
//                    imageVector = Icons.Default.History,
//                    contentDescription = "History",
//                    tint = Color.Gray
//                )
                
                Spacer(modifier = Modifier.padding(start = 12.dp))
                
                Text(
                    text = history.keyword,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
            
            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    val sampleSearchHistories = listOf(
        SearchHistoryEntity(
            id = 1,
            keyword = "Rick",
            timestamp = System.currentTimeMillis()
        ),
        SearchHistoryEntity(
            id = 2,
            keyword = "Morty",
            timestamp = System.currentTimeMillis() - 1000
        ),
        SearchHistoryEntity(
            id = 3,
            keyword = "Summer",
            timestamp = System.currentTimeMillis() - 2000
        )
    )

    BejanaTestTheme {
        SearchScreenContent(
            uiState = SearchUiState.Success(
                characters = emptyList(),
                searchHistories = sampleSearchHistories,
                isSearchBarFocused = false
            ),
            favoriteCharacters = emptyList(),
            initialQuery = "",
            onNavigateBack = {},
            onNavigateToDetail = {},
            onSearchCharacters = {},
            onSearchBarFocusChanged = {},
            onDeleteSearchHistory = {},
            onClearAllHistory = {},
            viewModel = object : SearchViewModel(
                api = null as Any as com.takehomechallenge.saputra.data.api.RickAndMortyApi,
                searchHistoryRepository = null as Any as com.takehomechallenge.saputra.data.repository.SearchHistoryRepository,
                favoriteRepository = null as Any as com.takehomechallenge.saputra.domain.repository.FavoriteRepository
            ) {
                override fun toggleFavorite(character: com.takehomechallenge.saputra.data.model.Character) {}
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenWithResultsPreview() {
    val sampleCharacters = listOf(
        Character(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = Location("Earth", ""),
            location = Location("Earth (Replacement Dimension)", ""),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf(),
            url = "",
            created = ""
        ),
        Character(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = Location("Earth", ""),
            location = Location("Earth (Replacement Dimension)", ""),
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            episode = listOf(),
            url = "",
            created = ""
        )
    )

    val mockViewModel = object : SearchViewModel(
        api = null as Any as com.takehomechallenge.saputra.data.api.RickAndMortyApi,
        searchHistoryRepository = null as Any as com.takehomechallenge.saputra.data.repository.SearchHistoryRepository,
        favoriteRepository = null as Any as com.takehomechallenge.saputra.domain.repository.FavoriteRepository
    ) {
        override fun toggleFavorite(character: com.takehomechallenge.saputra.data.model.Character) {}
    }

    BejanaTestTheme {
        SearchScreenContent(
            uiState = SearchUiState.Success(
                characters = sampleCharacters,
                searchHistories = emptyList(),
                query = "Rick"
            ),
            favoriteCharacters = emptyList(),
            initialQuery = "Rick",
            onNavigateBack = {},
            onNavigateToDetail = {},
            onSearchCharacters = {},
            onSearchBarFocusChanged = {},
            onDeleteSearchHistory = {},
            onClearAllHistory = {},
            viewModel = mockViewModel
        )
    }
}
