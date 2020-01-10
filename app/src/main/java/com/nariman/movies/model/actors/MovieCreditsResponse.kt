package com.nariman.movies.model.actors


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieCreditsResponse(
    val cast: List<Cast>,
    val id: Int
)