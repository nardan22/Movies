package com.nariman.movies.network

import com.nariman.movies.model.actors.MovieCreditsResponse
import com.nariman.movies.model.castinfo.CastDetails
import com.nariman.movies.model.castinfo.CastMovies
import com.nariman.movies.model.movie.*
import com.nariman.movies.model.relatedmovies.RelatedMoviesResponse
import com.nariman.movies.model.trailers.VideosResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRequestInterface {

    // Get Movie Details By it's ID
    @GET("movie/{movie_id}")
    fun getMovieById(
        @Path("movie_id")movie_id: Int,
        @Query("api_key")apiKey: String
    ): Deferred<Movie>

    // Get Popular movies list page by page
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page")page: Int,
        @Query("api_key")apiKey: String
    ): Response<PopularMoviesResponse>

    // Get Top Rated movies list page by page
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page")page: Int,
        @Query("api_key")apiKey: String
    ): Response<TopRatedMoviesResponse>

    // Get Upcoming movies list page by page
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page")page: Int,
        @Query("api_key")apiKey: String
    ): Response<UpcomingMoviesResponse>


    // Get Now Playing movies list page by page
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page")page: Int,
        @Query("api_key")apiKey: String
    ): Response<NowPlayingMoviesResponse>

    // Get Trailer of movie
    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id")movieId: Int,
        @Query("api_key")apiKey: String
    ): Response<VideosResponse>

    // Get Casts and Crews of movie
    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id")movieId: Int,
        @Query("api_key")apiKey: String
    ): Response<MovieCreditsResponse>

    // Get Recommended movies list
    @GET("movie/{movie_id}/recommendations")
    suspend fun getRelatedMovies(
        @Path("movie_id")movieId: Int,
        @Query("page")page: Int,
        @Query("api_key")apiKey: String
    ): Response<RelatedMoviesResponse>

    // Get Detail info about Cast
    @GET("person/{person_id}")
    suspend fun getCastDetails(
        @Path("person_id")personId: Int,
        @Query("api_key")apiKey:String
    ): Response<CastDetails>

    // Get Cast Movies
    @GET("person/{person_id}/movie_credits")
    suspend fun getCastMovieCredits(
        @Path("person_id")personId: Int,
        @Query("api_key")apiKey: String
    ): Response<CastMovies>
}