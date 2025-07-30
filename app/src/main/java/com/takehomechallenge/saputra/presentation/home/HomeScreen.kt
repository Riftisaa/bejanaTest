package com.takehomechallenge.saputra.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.takehomechallenge.saputra.R
import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.data.model.Location
import com.takehomechallenge.saputra.presentation.common.SearchBar
import com.takehomechallenge.saputra.presentation.home.components.CharacterItem
import com.takehomechallenge.saputra.ui.theme.BejanaTestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSearch: (String) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onMenuClick: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteCharacters by viewModel.favoriteCharacters.collectAsStateWithLifecycle()
    
    HomeScreenContent(
        uiState = uiState,
        favoriteCharacters = favoriteCharacters,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToDetail = onNavigateToDetail,
        onMenuClick = onMenuClick,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    favoriteCharacters: List<Character>,
    onNavigateToSearch: (String) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onMenuClick: () -> Unit,
    viewModel: HomeViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var showAllItems by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.primary_background),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Navbar dengan logo baru di kiri dan menu button di kanan
            NavBar(onMenuClick = onMenuClick)
            
            Spacer(modifier = Modifier.height(32.dp))

            // Logo utama di tengah (logo_1 yang asli)
            Image(
                painter = painterResource(id = R.drawable.logo_1),
                contentDescription = "Logo 1",
                modifier = Modifier
                    .fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            // Search bar - navigate to search screen when clicked
            SearchBar(
                query = searchQuery,
                onQueryChange = { query ->
                    searchQuery = query
                },
                onFocusChanged = { focused ->
                    if (focused) {
                        onNavigateToSearch(searchQuery)
                    }
                },
                onSearchSubmit = { query ->
                    onNavigateToSearch(query)
                },
                backgroundColor = Color(0xFF87F54E)
            )

            when (uiState) {
                is HomeUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is HomeUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.message,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                is HomeUiState.Success -> {
                    val itemsToShow = if (showAllItems) {
                        uiState.characters
                    } else {
                        uiState.characters.take(3) // Tampilkan maksimal 3 item
                    }
                    
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(itemsToShow) { character ->
                            CharacterItem(
                                character = character,
                                onCharacterClick = onNavigateToDetail,
                                onFavoriteClick = { character ->
                                    viewModel.toggleFavorite(character)
                                },
                                isFavorite = favoriteCharacters.any { it.id == character.id }
                            )
                        }
                        
                        // Tampilkan tombol Load More jika belum menampilkan semua item
                        if (!showAllItems && uiState.characters.size > 3) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Button(
                                        onClick = { showAllItems = true },
                                        modifier = Modifier
                                            .width(146.dp)
                                            .height(36.dp),
                                        shape = RoundedCornerShape(4.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF87F54E)
                                        )
                                    ) {
                                        Text(
                                            text = "LOAD MORE",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Simple preview component for testing
@Composable
private fun HomeScreenPreviewContent(
    uiState: HomeUiState
) {
    var searchQuery by remember { mutableStateOf("") }
    var showAllItems by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.primary_background),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Navbar untuk preview
            NavBar(onMenuClick = {})
            
            Spacer(modifier = Modifier.height(32.dp))

            // Logo utama di tengah
            Image(
                painter = painterResource(id = R.drawable.logo_1),
                contentDescription = "Logo 1",
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            // Preview berdasarkan state
            when (uiState) {
                is HomeUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF87F54E))
                    }
                }
                is HomeUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.message,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
                is HomeUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.characters.take(2)) { character ->
                            CharacterItem(
                                character = character,
                                onCharacterClick = {},
                                onFavoriteClick = {},
                                isFavorite = false
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenLoadingPreview() {
    BejanaTestTheme {
        HomeScreenPreviewContent(
            uiState = HomeUiState.Loading
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenErrorPreview() {
    BejanaTestTheme {
        HomeScreenPreviewContent(
            uiState = HomeUiState.Error("Failed to load characters")
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenSuccessPreview() {
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
        ),
        Character(
            id = 3,
            name = "Summer Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Female",
            origin = Location("Earth", ""),
            location = Location("Earth (Replacement Dimension)", ""),
            image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
            episode = listOf(),
            url = "",
            created = ""
        ),
        Character(
            id = 4,
            name = "Beth Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Female",
            origin = Location("Earth", ""),
            location = Location("Earth (Replacement Dimension)", ""),
            image = "https://rickandmortyapi.com/api/character/avatar/4.jpeg",
            episode = listOf(),
            url = "",
            created = ""
        ),
        Character(
            id = 5,
            name = "Jerry Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = Location("Earth", ""),
            location = Location("Earth (Replacement Dimension)", ""),
            image = "https://rickandmortyapi.com/api/character/avatar/5.jpeg",
            episode = listOf(),
            url = "",
            created = ""
        )
    )

    BejanaTestTheme {
        HomeScreenPreviewContent(
            uiState = HomeUiState.Success(
                characters = sampleCharacters
            )
        )
    }
}


@Composable
private fun NavBar(
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo baru di kiri (placeholder - akan diganti dengan logo baru nanti)
        Image(
            painter = painterResource(id = R.drawable.navbar_icon), // Placeholder
            contentDescription = "Logo Navbar",
            modifier = Modifier
                .height(60.dp)
                .width(60.dp)
        )

        // Menu button di kanan
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color(0xFF87F54E),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NavBarPreview() {
    BejanaTestTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .paint(
                    painterResource(id = R.drawable.primary_background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(16.dp)
        ) {
            NavBar(onMenuClick = {})
        }
    }
} 