package com.nariman.movies.model.trailers


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieVideoInfo(
    val id: String,
    val key: String?,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)