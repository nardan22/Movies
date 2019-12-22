package com.nariman.movies.moviedetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nariman.movies.database.MovieRoomDatabase
import com.nariman.movies.model.MovieDetailsEntity
import com.nariman.movies.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application, val movieId: Int) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = MovieRoomDatabase.getDatabase(application.applicationContext)

    private val repository = Repository(application.applicationContext, database)

    private val _movieDetails = MutableLiveData<MovieDetailsEntity>()
    val movieDetails: LiveData<MovieDetailsEntity> = database.movieDAO().getMovieDetailsById(movieId)

    init {
        viewModelScope.launch {
            repository.loadMovieDetailsFromNetworkIntoDatabase(movieId)
        }
        Log.i("MovieDetail", "In MovieDetailViewModel class initialization")
    }

    override fun onCleared() {
        super.onCleared()
        if (!viewModelJob.isCancelled) viewModelJob.cancel()
    }
}