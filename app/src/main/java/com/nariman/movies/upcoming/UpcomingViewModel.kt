package com.nariman.movies.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nariman.movies.model.movie.Movie
import com.nariman.movies.repository.MovieDataSource
import com.nariman.movies.util.LOAD_SIZE_HINT
import com.nariman.movies.util.POST_PER_PAGE
import com.nariman.movies.util.PREFETCH_DISTANCE
import kotlinx.coroutines.Dispatchers

class UpcomingViewModel : ViewModel() {

    private val _navigateToMovieDetails = MutableLiveData<Movie>()
    val navigateToMovieDetails: LiveData<Movie> get() = _navigateToMovieDetails

    private val upcomingMoviesLivePagedList: LiveData<PagedList<Movie>>

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(LOAD_SIZE_HINT)
            .setPageSize(POST_PER_PAGE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .build()

        upcomingMoviesLivePagedList = initializedLiveDataSourceBuilder(config).build()
    }

    fun getUpcomingMoviesPagedList():LiveData<PagedList<Movie>> {
        return upcomingMoviesLivePagedList
    }

    private fun initializedLiveDataSourceBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Movie> {

        val dataSourceFactory = object : DataSource.Factory<Int, Movie>(){
            override fun create(): DataSource<Int, Movie> {
                return MovieDataSource(Dispatchers.IO, "upcoming")
            }
        }

        return LivePagedListBuilder(dataSourceFactory, config)
    }

    fun onClickNavigation(movie: Movie) {
        _navigateToMovieDetails.value = movie
    }

    fun onNavigationDone(){
        _navigateToMovieDetails.value = null
    }
}
