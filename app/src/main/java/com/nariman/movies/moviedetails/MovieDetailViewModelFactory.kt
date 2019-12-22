package com.nariman.movies.moviedetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class MovieDetailViewModelFactory(val application: Application, val movieId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Application::class.java, Int::class.java).newInstance(application, movieId)
    }

}