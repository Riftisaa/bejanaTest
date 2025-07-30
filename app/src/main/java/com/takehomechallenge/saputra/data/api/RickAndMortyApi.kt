package com.takehomechallenge.saputra.data.api

import com.takehomechallenge.saputra.data.model.ApiResponse
import com.takehomechallenge.saputra.data.model.Character
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null
    ): ApiResponse<Character>

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): Character
} 