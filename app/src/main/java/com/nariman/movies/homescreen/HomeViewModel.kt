package com.nariman.movies.homescreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nariman.movies.model.Movie
import com.nariman.movies.repository.MovieDataSource
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class HomeViewModel : ViewModel(){

    private val _navigateToMovieDetails = MutableLiveData<Movie>()
    val navigateToMovieDetails: LiveData<Movie> get() = _navigateToMovieDetails

    private val executor: Executor

    private val moviesPagedList: LiveData<PagedList<Movie>>


    init {

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .setPrefetchDistance(4)
            .build()

        executor = Executors.newFixedThreadPool(5)

        moviesPagedList = initializedLivePagedListBuilder(config).build()

    }

    fun getMoviesPagedList(): LiveData<PagedList<Movie>> {
        Log.i("popularMovies", "In getMoviesPagedList() method: ${moviesPagedList.value?.size}")
        return moviesPagedList
    }

    fun onClickNavigation(movie: Movie){
        Log.i("popularMovies", "Clicked Movie Id: ${movie.id}")
        _navigateToMovieDetails.value = movie
    }

    fun navigationDone(){
        _navigateToMovieDetails.value = null
    }

    fun initializedLivePagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, Movie>{

        val dataSourceFactory = object : DataSource.Factory<Int, Movie>(){
            override fun create(): DataSource<Int, Movie> {
                return MovieDataSource(Dispatchers.IO)
            }
        }

        return LivePagedListBuilder<Int, Movie>(dataSourceFactory, config)
    }
}