package com.nariman.movies.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int,
    val title: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "poster_path")
    val posterPath: String
)