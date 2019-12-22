package com.nariman.movies.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularMoviesResponse(
    @Json(name = "results")
    val movies: List<Movie>,
    val page: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)