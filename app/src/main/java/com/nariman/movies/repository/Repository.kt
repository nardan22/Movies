package com.nariman.movies.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nariman.movies.model.actors.Cast
import com.nariman.movies.model.castinfo.CastDetails
import com.nariman.movies.model.castinfo.CastMovieInfo
import com.nariman.movies.model.movie.Movie
import com.nariman.movies.model.trailers.VideosResponse
import com.nariman.movies.network.RetrofitInstance
import com.nariman.movies.util.API_KEY

class Repository() {


    // movie details
    val movie = MutableLiveData<Movie>()

    // movie trailer
    val movieVideos = MutableLiveData<VideosResponse>()

    // cast's list of certain movie
    val actorsList = MutableLiveData<List<Cast>>()

    // cast details
    val castDetails = MutableLiveData<CastDetails>()

    // cast starred movies
    val castMovies = MutableLiveData<List<CastMovieInfo>>()




    // load movie details
    suspend fun getMovieById(movieId: Int) {
        try {
            val response = RetrofitInstance.movieRequest.getMovieById(movieId, API_KEY)
            movie.postValue(response.await())
        } catch (e: Exception){
            e.stackTrace
        }
    }



    // load movie videos info
    suspend fun getMovieVideos(movieId: Int){
        try {
            val response = RetrofitInstance.movieRequest.getVideos(movieId, API_KEY)
            if (response.isSuccessful){
                movieVideos.postValue(response.body())
            }
        } catch (e: Exception){
            e.stackTrace
        }
    }



    // load movie credits info
    suspend fun getCredits(movieId: Int){
        try {
            val response = RetrofitInstance.movieRequest.getMovieCredits(movieId, API_KEY)

            if (response.isSuccessful){

                if (response.body()!!.cast.size > 10) {
                    actorsList.postValue(response.body()!!.cast.subList(0, 10))
                }
                else {
                    actorsList.postValue(response.body()!!.cast)
                }

            }
        } catch (e: Exception){
            e.stackTrace
        }
    }



    // load cast detail info
    suspend fun getCastDetails(castId: Int){
        try {
            val response = RetrofitInstance.movieRequest.getCastDetails(castId, API_KEY)
            if (response.isSuccessful) castDetails.postValue(response.body())
        } catch (e: Exception){
            e.stackTrace
        }
    }



    // load cast starred movies
    suspend fun getCastMovies(castId: Int){
        try {
            val response = RetrofitInstance.movieRequest.getCastMovieCredits(castId, API_KEY)
            Log.i("someerror", "Cast Movies: ${response.body()}")
            if (response.isSuccessful){
                castMovies.postValue(response.body()!!.cast)
            }
        } catch (e: Exception){
            Log.i("someerror", "Cast Movies Exception: ${e.message}")
            e.stackTrace
        }
    }
}