package com.nariman.movies.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.nariman.movies.model.movie.Movie
import com.nariman.movies.network.RetrofitInstance
import com.nariman.movies.util.API_KEY
import com.nariman.movies.util.FIRST_PAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDataSource(coroutineContext: CoroutineContext, val requestType: String, movieID: Int = 0) : PageKeyedDataSource<Int, Movie>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)
    private val movieId = movieID

    private val apiService = RetrofitInstance.movieRequest

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>)
    {
        scope.launch {

            when(requestType){
                "popular" -> {
                    try {
                        val popularMoviesResponse = apiService.getPopularMovies(FIRST_PAGE, API_KEY)
                        if (popularMoviesResponse.isSuccessful) {
                            callback.onResult(popularMoviesResponse.body()!!.movies, null, 2)
                        }
                    } catch (e: Exception) {
                        Log.i("someerror", "Some Error: ${e.message}")
                        e.stackTrace
                    }
                }

                "topRated" -> {
                    try {
                        val topRatedMoviesResponse = apiService.getTopRatedMovies(FIRST_PAGE, API_KEY)
                        if (topRatedMoviesResponse.isSuccessful) {
                            callback.onResult(topRatedMoviesResponse.body()!!.movies, null, 2)
                        }
                    } catch (e: Exception){
                        e.stackTrace
                    }
                }

                "upcoming" -> {
                    try {
                        val upcomingMoviesResponse = apiService.getUpcomingMovies(FIRST_PAGE, API_KEY)
                        if (upcomingMoviesResponse.isSuccessful){
                            callback.onResult(upcomingMoviesResponse.body()!!.movies, 0, 2)
                        }
                    } catch (e: Exception){
                        e.stackTrace
                    }
                }

                "nowPlaying" -> {
                    try {
                        val nowPlayingMoviesResponse = apiService.getNowPlayingMovies(FIRST_PAGE, API_KEY)
                        if (nowPlayingMoviesResponse.isSuccessful){
                            callback.onResult(nowPlayingMoviesResponse.body()!!.movies, 0, 2)
                        }
                    } catch (e: Exception){
                        e.stackTrace
                    }
                }

                "related" -> {
                    try {
                        val relatedMoviesResponse = apiService.getRelatedMovies(movieId, FIRST_PAGE, API_KEY)
                        if (relatedMoviesResponse.isSuccessful){
                            callback.onResult(relatedMoviesResponse.body()!!.results, 0, 2)
                        }
                    } catch (e: Exception){
                        e.stackTrace
                    }
                }
            }
        }
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch {
            when(requestType){

                "popular" -> {
                    try {
                        val popularMoviesResponse = apiService.getPopularMovies(params.key, API_KEY)
                        if (popularMoviesResponse.isSuccessful) {
                            callback.onResult(popularMoviesResponse.body()!!.movies, params.key.inc())
                        }
                    } catch (e: Exception) {
                        e.stackTrace
                    }
                }

                "topRated" -> {
                    try {
                        val topRatedMoviesResponse = apiService.getTopRatedMovies(params.key, API_KEY)
                        if (topRatedMoviesResponse.isSuccessful) {
                            callback.onResult(topRatedMoviesResponse.body()!!.movies, params.key.inc())
                        }
                    } catch (e: Exception) {
                        e.stackTrace
                    }
                }

                "upcoming" -> {
                    try {
                        val upcomingMoviesResponse = apiService.getUpcomingMovies(params.key, API_KEY)
                        if (upcomingMoviesResponse.isSuccessful){
                            callback.onResult(upcomingMoviesResponse.body()!!.movies, params.key.inc())
                        }
                    } catch (e: Exception){
                        e.stackTrace
                    }
                }

                "nowPlaying" -> {
                    try {
                        val nowPlayingMoviesResponse = apiService.getNowPlayingMovies(params.key, API_KEY)
                        if (nowPlayingMoviesResponse.isSuccessful){
                            callback.onResult(nowPlayingMoviesResponse.body()!!.movies, params.key.inc())
                        }
                    } catch (e: Exception){
                        e.stackTrace
                    }
                }

                "related" -> {
                    try {
                        val relatedMoviesResponse = apiService.getRelatedMovies(movieId, params.key, API_KEY)
                        if (relatedMoviesResponse.isSuccessful){
                            callback.onResult(relatedMoviesResponse.body()!!.results, params.key.inc())
                        }
                    } catch (e: Exception){
                        e.stackTrace
                    }
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}