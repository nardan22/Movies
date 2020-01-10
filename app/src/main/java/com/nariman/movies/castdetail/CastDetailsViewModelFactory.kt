package com.nariman.movies.castdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

// ViewModel Constructor
class CastDetailsViewModelFactory (val application: Application, val castId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Application::class.java, Int::class.java).newInstance(application, castId)
    }
}