package com.nariman.movies.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nariman.movies.model.MovieDetailsEntity

@Dao
interface MovieDAO {

    @Query("SELECT * FROM movies WHERE movie_id=:movieID ")
    fun getMovieDetailsById(movieID: Int): LiveData<MovieDetailsEntity>

    @Insert(entity = MovieDetailsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieDetailsEntity)
}