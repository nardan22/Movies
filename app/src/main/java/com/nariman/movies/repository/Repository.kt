package com.nariman.movies.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nariman.movies.database.MovieRoomDatabase
import com.nariman.movies.model.MovieDetailsEntity
import com.nariman.movies.model.asDatabaseEntity
import com.nariman.movies.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(context: Context, private val database: MovieRoomDatabase) {

    suspend fun loadMovieDetailsFromNetworkIntoDatabase(movieId: Int){

        withContext(Dispatchers.IO){
            // Fetching Movie details by given Id
            val movieDetailsFromNetwork = RetrofitInstance.movieRequest.getMovieById(movieId).await()
            // Writing fetched movie details into database
            database.movieDAO().insertMovie(movieDetailsFromNetwork.asDatabaseEntity())
        }
    }
}