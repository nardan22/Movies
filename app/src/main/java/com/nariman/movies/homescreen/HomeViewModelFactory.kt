package com.nariman.movies.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return modelClass.newInstance()
        }

        throw IllegalArgumentException("Unable to construct viewModel")
    }

}