package com.takehomechallenge.saputra.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.takehomechallenge.saputra.R
import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.data.model.Location
import com.takehomechallenge.saputra.presentation.home.components.CharacterItem
import com.takehomechallenge.saputra.ui.theme.BejanaTestTheme

@Composable
fun FavoriteScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoriteCharacters by viewModel.favoriteCharacters.collectAsStateWithLifecycle()
    
    FavoriteScreenContent(
        favoriteCharacters = favoriteCharacters,
        onNavigateToDetail = onNavigateToDetail,
        onFavoriteClick = { character ->
            viewModel.removeFavorite(character)
        }
    )
}

@Composable
private fun FavoriteScreenContent(
    favoriteCharacters: List<Character>,
    onNavigateToDetail: (Int) -> Unit,
    onFavoriteClick: (Character) -> Unit
) {
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

            // Title
            Text(
                text = "Favorite Characters",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF87F54E),
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            when {
                favoriteCharacters.isEmpty() -> {
                    // Empty state
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No favorite characters yet",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Add characters to favorites by tapping the heart icon",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                
                else -> {
                    // Favorite characters list
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(favoriteCharacters) { character ->
                            CharacterItem(
                                character = character,
                                onCharacterClick = onNavigateToDetail,
                                onFavoriteClick = onFavoriteClick,
                                isFavorite = true // Always true in favorite screen
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
private fun FavoriteScreenEmptyPreview() {
    BejanaTestTheme {
        FavoriteScreenContent(
            favoriteCharacters = emptyList(),
            onNavigateToDetail = {},
            onFavoriteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteScreenWithDataPreview() {
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

    BejanaTestTheme {
        FavoriteScreenContent(
            favoriteCharacters = sampleCharacters,
            onNavigateToDetail = {},
            onFavoriteClick = {}
        )
    }
}
