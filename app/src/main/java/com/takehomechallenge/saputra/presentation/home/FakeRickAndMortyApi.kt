package com.takehomechallenge.saputra.presentation.home

import com.takehomechallenge.saputra.data.api.RickAndMortyApi
import com.takehomechallenge.saputra.data.model.ApiResponse
import com.takehomechallenge.saputra.data.model.Character
import com.takehomechallenge.saputra.data.model.Info
import com.takehomechallenge.saputra.data.model.Location

class FakeRickAndMortyApi : RickAndMortyApi {
    override suspend fun getCharacters(page: Int?, name: String?): ApiResponse<Character> {
        return ApiResponse(
            info = Info(
                count = 2,
                pages = 1,
                next = null,
                prev = null
            ),
            results = listOf(
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
        )
    }

    override suspend fun getCharacter(id: Int): Character {
        return Character(
            id = id,
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
        )
    }
} 