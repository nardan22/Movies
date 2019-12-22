package com.nariman.movies.moviedetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.nariman.movies.databinding.MovieDetailsFragmentBinding
import com.nariman.movies.repository.Repository

class MovieDetailFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = MovieDetailsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val movieId = MovieDetailFragmentArgs.fromBundle(arguments!!).movieId

        val viewModelFactory = MovieDetailViewModelFactory(activity!!.application, movieId)
        val movieDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MovieDetailViewModel::class.java)
        binding.movieDetailViewModel = movieDetailViewModel


        return binding.root
    }
}