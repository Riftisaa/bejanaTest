package com.takehomechallenge.saputra.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.data.model.Location
import com.takehomechallenge.saputra.ui.theme.BejanaTestTheme

@Composable
fun CharacterItem(
    character: Character,
    onCharacterClick: (Int) -> Unit,
    onFavoriteClick: (Character) -> Unit = {},
    isFavorite: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCharacterClick(character.id) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF87F54E)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Bagian teks (nama dan species)
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = character.species,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Thin,
                        color = Color.Black
                    )
                }

                // Icon favorite di sebelah kanan
                IconButton(
                    onClick = { onFavoriteClick(character) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (isFavorite) Color.Red else Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))


            }
        }
    }


@Preview(showBackground = true)
@Composable
fun CharacterItemPreview() {
    BejanaTestTheme {
        CharacterItem(
            character = Character(
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
            onCharacterClick = {},
            onFavoriteClick = {},
            isFavorite = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterItemFavoritePreview() {
    BejanaTestTheme {
        CharacterItem(
            character = Character(
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
            onCharacterClick = {},
            onFavoriteClick = {},
            isFavorite = true
        )
    }
} 