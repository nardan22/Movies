package com.nariman.movies.nowplaying

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

class NowPlayingViewModel : ViewModel() {

    private val nowPlayingMovies: LiveData<PagedList<Movie>>

    private val _navigateToMovieDetails = MutableLiveData<Movie>()
    val navigateToMovieDetails: LiveData<Movie> get() = _navigateToMovieDetails

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(LOAD_SIZE_HINT)
            .setPageSize(POST_PER_PAGE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .build()

        nowPlayingMovies = initializeLivePagedListBuilder(config).build()
    }

    fun onClickNavigation(movie: Movie){
        _navigateToMovieDetails.value =movie
    }

    fun onClickNavigationDone(){
        _navigateToMovieDetails.value = null
    }

    fun getNowPlayingMovies() = nowPlayingMovies

    private fun initializeLivePagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, Movie> {
        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return MovieDataSource(Dispatchers.IO, "nowPlaying")
            }
        }
        return  LivePagedListBuilder(dataSourceFactory, config)
    }
}
