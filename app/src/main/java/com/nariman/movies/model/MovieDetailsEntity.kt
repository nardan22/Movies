package com.nariman.movies.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDetailsEntity constructor (
    @PrimaryKey
    val movie_id: Int,
    val budget: Int,
    val overview: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val tagline: String,
    val title: String,
    val rating: Double
)