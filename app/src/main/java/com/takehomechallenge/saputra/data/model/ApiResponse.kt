package com.takehomechallenge.saputra.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    @Json(name = "info")
    val info: Info,
    @Json(name = "results")
    val results: List<T>
)

@JsonClass(generateAdapter = true)
data class Info(
    @Json(name = "count")
    val count: Int,
    @Json(name = "pages")
    val pages: Int,
    @Json(name = "next")
    val next: String?,
    @Json(name = "prev")
    val prev: String?
) 