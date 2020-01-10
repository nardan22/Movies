package com.nariman.movies.castdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nariman.movies.model.castinfo.CastDetails
import com.nariman.movies.model.castinfo.CastMovieInfo
import com.nariman.movies.repository.Repository
import kotlinx.coroutines.*

class CastDetailsViewModel (application: Application, castId: Int) : AndroidViewModel(application) {

    // Initialize repository
    val repository = Repository()

    // for performing data in background without blocking UI thread
    val job = Job()
    val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    // cast details
    val castDetails: LiveData<CastDetails> = repository.castDetails

    // cast's starred movies
    private val castMovies: LiveData<List<CastMovieInfo>> = repository.castMovies

    // navigate variables to navigate to MovieDetailFragment
    private val _navigateToMovieDetails = MutableLiveData<CastMovieInfo>()
    val navigateToMovieDetails: LiveData<CastMovieInfo> get() = _navigateToMovieDetails

    init {
        // task in background
        coroutineScope.launch {
            // request to get cast's detail information
            repository.getCastDetails(castId)

            // request to get cast't starred movies
            repository.getCastMovies(castId)
        }
    }

    // Getter of castMovies
    fun getCastMovies(): LiveData<List<CastMovieInfo>> = castMovies

    override fun onCleared() {
        // cancel all background tasks
        coroutineScope.cancel()
        super.onCleared()
    }

    // on item click navigation
    fun movieDetailsOnClickNavigation(castMovieInfo: CastMovieInfo) {
        _navigateToMovieDetails.value = castMovieInfo
    }

    // reset after navigation done
    fun onMovieDetailsNavigationDone(){
        _navigateToMovieDetails.value = null
    }
}
