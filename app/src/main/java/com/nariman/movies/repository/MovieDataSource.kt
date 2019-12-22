package com.nariman.movies.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.nariman.movies.model.Movie
import com.nariman.movies.network.RetrofitInstance
import com.nariman.movies.util.API_KEY
import com.nariman.movies.util.FIRST_PAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDataSource(coroutineContext: CoroutineContext) : PageKeyedDataSource<Int, Movie>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    private val apiService = RetrofitInstance.movieRequest

    val movies = MutableLiveData<PagedList<Movie>>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {

        scope.launch {
            try {
                val popularMoviesResponse = apiService.getPopularMovies(FIRST_PAGE, API_KEY)
                if (popularMoviesResponse.isSuccessful) {
                    callback.onResult(popularMoviesResponse.body()!!.movies, null, 2)
                }
            } catch (e: Exception) {
                e.stackTrace
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch {
            try {
                val popularMoviesResponse = apiService.getPopularMovies(params.key, API_KEY)
                if (popularMoviesResponse.isSuccessful) {
                    callback.onResult(popularMoviesResponse.body()!!.movies, params.key.inc())
                }
            } catch (e: Exception) {
                e.stackTrace
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