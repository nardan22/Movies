package com.nariman.movies.model.castinfo


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CastDetails(
    val biography: String?,
    val birthday: String?,
    val deathday: String?,
    val gender: Int,
    val id: Int,
    val name: String,
    @Json(name = "place_of_birth")
    val placeOfBirth: String?,
    @Json(name = "profile_path")
    val profilePath: String?
)