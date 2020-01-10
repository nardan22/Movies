package com.nariman.movies.model.trailers


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideosResponse(
    val id: Int,
    val results: List<MovieVideoInfo>
)