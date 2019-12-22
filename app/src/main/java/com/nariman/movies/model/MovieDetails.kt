package com.nariman.movies.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetails(
    val budget: Int,
    val id: Int,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val tagline: String,
    val title: String,
    @Json(name = "vote_average")
    val rating: Double
)

fun MovieDetails.asDatabaseEntity(): MovieDetailsEntity {
    return MovieDetailsEntity(
        movie_id = this.id,
        budget = this.budget,
        overview = this.overview,
        posterPath = this.posterPath,
        rating = this.rating,
        releaseDate = this.releaseDate,
        revenue = this.revenue,
        runtime = this.runtime,
        tagline = this.tagline,
        title = this.title
    )
}