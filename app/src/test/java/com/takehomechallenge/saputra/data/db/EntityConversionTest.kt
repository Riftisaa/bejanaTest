package com.takehomechallenge.saputra.data.db

import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.data.model.Location
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for database entity conversion extensions
 * Tests conversion between Character and FavoriteCharacterEntity
 */
class EntityConversionTest {

    private val testCharacter = Character(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = Location("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
        location = Location("Citadel of Ricks", "https://rickandmortyapi.com/api/location/3"),
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        episode = listOf("https://rickandmortyapi.com/api/episode/1", "https://rickandmortyapi.com/api/episode/2"),
        url = "https://rickandmortyapi.com/api/character/1",
        created = "2017-11-04T18:48:46.250Z"
    )

    private val testEntity = FavoriteCharacterEntity(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        originName = "Earth (C-137)",
        originUrl = "https://rickandmortyapi.com/api/location/1",
        locationName = "Citadel of Ricks",
        locationUrl = "https://rickandmortyapi.com/api/location/3",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        episodes = "https://rickandmortyapi.com/api/episode/1,https://rickandmortyapi.com/api/episode/2",
        url = "https://rickandmortyapi.com/api/character/1",
        created = "2017-11-04T18:48:46.250Z"
    )

    @Test
    fun `Character toEntity should convert correctly`() {
        // When
        val entity = testCharacter.toEntity()

        // Then
        assertEquals(testCharacter.id, entity.id)
        assertEquals(testCharacter.name, entity.name)
        assertEquals(testCharacter.status, entity.status)
        assertEquals(testCharacter.species, entity.species)
        assertEquals(testCharacter.origin.name, entity.originName)
        assertEquals(testCharacter.origin.url, entity.originUrl)
        assertEquals(testCharacter.location.name, entity.locationName)
        assertEquals(testCharacter.location.url, entity.locationUrl)
        assertEquals(testCharacter.episode.joinToString(","), entity.episodes)
    }

    @Test
    fun `FavoriteCharacterEntity toCharacter should convert correctly`() {
        // When
        val character = testEntity.toCharacter()

        // Then
        assertEquals(testEntity.id, character.id)
        assertEquals(testEntity.name, character.name)
        assertEquals(testEntity.status, character.status)
        assertEquals(testEntity.species, character.species)
        assertEquals(testEntity.originName, character.origin.name)
        assertEquals(testEntity.originUrl, character.origin.url)
        assertEquals(testEntity.locationName, character.location.name)
        assertEquals(testEntity.locationUrl, character.location.url)
        assertEquals(testEntity.episodes.split(","), character.episode)
    }

    @Test
    fun `Conversion should handle empty episodes correctly`() {
        // Given
        val characterWithEmptyEpisodes = testCharacter.copy(episode = emptyList())
        val entityWithEmptyEpisodes = testEntity.copy(episodes = "")

        // When
        val entity = characterWithEmptyEpisodes.toEntity()
        val character = entityWithEmptyEpisodes.toCharacter()

        // Then
        assertEquals("", entity.episodes)
        assertEquals(emptyList<String>(), character.episode)
    }
}
