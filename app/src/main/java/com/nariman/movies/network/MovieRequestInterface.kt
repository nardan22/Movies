package com.nariman.movies.network

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.nariman.movies.model.Movie
import com.nariman.movies.model.MovieDetails
import com.nariman.movies.model.PopularMoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRequestInterface {
    @GET("movie/{movie_id}?api_key=306833900abf8ea9f54dabab41fd713e")
    fun getMovieById(@Path("movie_id")movie_id: Int): Deferred<MovieDetails>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page")page: Int, @Query("api_key")apiKey: String): Response<PopularMoviesResponse>
}