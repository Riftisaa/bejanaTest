package com.takehomechallenge.saputra.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.data.model.Location

@Entity(tableName = "favorite_characters")
data class FavoriteCharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val originName: String,
    val originUrl: String,
    val locationName: String,
    val locationUrl: String,
    val image: String,
    val episodes: String, // JSON string for episode list
    val url: String,
    val created: String,
    val timestamp: Long = System.currentTimeMillis()
)

// Extension functions for conversion
fun Character.toEntity(): FavoriteCharacterEntity {
    return FavoriteCharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        originName = origin.name,
        originUrl = origin.url,
        locationName = location.name,
        locationUrl = location.url,
        image = image,
        episodes = episode.joinToString(","),
        url = url,
        created = created
    )
}

fun FavoriteCharacterEntity.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = Location(originName, originUrl),
        location = Location(locationName, locationUrl),
        image = image,
        episode = if (episodes.isNotEmpty()) episodes.split(",") else emptyList(),
        url = url,
        created = created
    )
}
