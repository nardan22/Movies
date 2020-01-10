package com.nariman.movies.moviedetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.nariman.movies.model.actors.Cast
import com.nariman.movies.model.movie.Movie
import com.nariman.movies.model.trailers.MovieVideoInfo
import com.nariman.movies.model.trailers.VideosResponse
import com.nariman.movies.repository.MovieDataSource
import com.nariman.movies.repository.Repository
import com.nariman.movies.util.LOAD_SIZE_HINT
import com.nariman.movies.util.POST_PER_PAGE
import com.nariman.movies.util.PREFETCH_DISTANCE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application, val movieId: Int) : AndroidViewModel(application) {

    // For Background Tasks
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    // Repository
    private val repository = Repository()

    // Movie details list
    val movieDetails: LiveData<Movie> = repository.movie

    // Movie Videos
    val movieVideos: LiveData<VideosResponse> = repository.movieVideos
    private val _movieVideoInfo = MutableLiveData<MovieVideoInfo>()
    val movieVideoInfo: LiveData<MovieVideoInfo> get() = _movieVideoInfo

    // Navigation to actor details
    private val _navigateToActorDetails = MutableLiveData<Cast>()
    val navigateToActorDetails: LiveData<Cast> get() = _navigateToActorDetails

    // Movie Casts
    val actorsList: LiveData<List<Cast>> = repository.actorsList

    // Recommendation Movies list
    private val relatedMoviesPagedList: LiveData<PagedList<Movie>>

    // Navigation to related MovieDetailsFragment
    private val _navigateToRelatedMovieDetails = MutableLiveData<Movie>()
    val navigateToRelatedMovieDetails: LiveData<Movie> get() = _navigateToRelatedMovieDetails

    init {
        viewModelScope.launch {
            repository.getMovieById(movieId)
            repository.getMovieVideos(movieId)
            repository.getCredits(movieId)
        }
        Log.i("MovieDetail", "In MovieDetailViewModel class initialization")

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(LOAD_SIZE_HINT)
            .setPageSize(POST_PER_PAGE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .build()

        relatedMoviesPagedList = initializedLivePagedListBuilder(config).build()
    }

    // Getter of relatedMoviesPagedList
    fun getRelatedMoviesPagedList() = relatedMoviesPagedList

    // LivePagedListBuilder initializer
    private fun initializedLivePagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Movie> {
        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return MovieDataSource(Dispatchers.IO, "related", movieId)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    fun onClickNavigationToRelatedMovieDetails(movie: Movie){
        _navigateToRelatedMovieDetails.value = movie
    }

    fun onNavigationToRelatedMovieDetailsDone(){
        _navigateToRelatedMovieDetails.value = null
    }

    // Setting movie video info function
    fun setMovieVideoInfo(){
        if (movieVideos.value!!.results.isNotEmpty()){
            _movieVideoInfo.value = movieVideos.value!!.results.find {
                it.type == "Trailer"
            }
        }
    }

    fun onClickNavigationToActorDetail(actor: Cast){
        _navigateToActorDetails.value = actor
    }

    // Navigation done function
    fun onNavigationToActorDetailsDone(){
        _navigateToActorDetails.value = null
    }

    // Cancel All Coroutines
    override fun onCleared() {
        Log.i("myfragments", "MovieDetailViewModel.onCleared()")

        super.onCleared()
        if (!viewModelJob.isCancelled) viewModelJob.cancel()
    }
}