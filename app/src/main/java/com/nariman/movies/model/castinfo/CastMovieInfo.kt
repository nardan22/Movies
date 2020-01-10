package com.nariman.movies.model.castinfo


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CastMovieInfo(
    val character: String,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    val title: String,
    @Json(name = "vote_average")
    val voteAverage: Double
)