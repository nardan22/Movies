package com.nariman.movies.model.castinfo


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CastMovies(
    val cast: List<CastMovieInfo>,
    val id: Int
)