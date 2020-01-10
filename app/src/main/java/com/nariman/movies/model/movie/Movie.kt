package com.nariman.movies.model.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int,
    val title: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "vote_average")
    val voteAverage: Float,
    @Json(name = "poster_path")
    val posterPath: String?,
    val overview: String,
    val revenue: Long?,
    val runtime: Int?,
    val tagline: String?,
    val budget: Long?
)