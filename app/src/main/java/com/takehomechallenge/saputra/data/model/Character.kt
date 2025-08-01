package com.takehomechallenge.saputra.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Character(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "species")
    val species: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "origin")
    val origin: Location,
    @Json(name = "location")
    val location: Location,
    @Json(name = "image")
    val image: String,
    @Json(name = "episode")
    val episode: List<String>,
    @Json(name = "url")
    val url: String,
    @Json(name = "created")
    val created: String
)

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
) 