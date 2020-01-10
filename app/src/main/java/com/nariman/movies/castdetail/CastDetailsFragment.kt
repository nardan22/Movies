package com.nariman.movies.castdetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.nariman.movies.R
import com.nariman.movies.databinding.CastDetailsFragmentBinding
import com.nariman.movies.model.actors.CastJsonAdapter
import kotlinx.android.synthetic.main.cast_details_fragment.*

class CastDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // binding view
        val binding = CastDetailsFragmentBinding.inflate(inflater)

        // get passed data
        val castId = CastDetailsFragmentArgs.fromBundle(arguments!!).castId

        // initialize viewmodel
        val viewModelFactory = CastDetailsViewModelFactory(activity!!.application, castId)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(CastDetailsViewModel::class.java)
        binding.castVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // initialize adapter
        val adapter = CastMoviesRecyclerViewAdapter(CastMoviesRecyclerViewAdapter.OnItemClickListener{
            viewModel.movieDetailsOnClickNavigation(it)
        })

        // bind adapter to recyclerview
        binding.starredMoviesRecyclerView.adapter = adapter

        // Observe castMovies change to show them in recyclerview
        viewModel.getCastMovies().observe(this, Observer {
            adapter.setCastMovies(it)
        })

        // Observe navigateToMovieDetails to any changes
        viewModel.navigateToMovieDetails.observe(this, Observer{
            // when change happened and if navigateToMovieDetails is not null
            if (it != null){
                // then navigate to movie details
                findNavController().navigate(
                    CastDetailsFragmentDirections.actionCastDetailsFragmentToMovieDetailFragment(it.id)
                )
                // finally set navigateToMovieDetails to null
                viewModel.onMovieDetailsNavigationDone()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        // set recyclerview adapter to null to avoid memory leak
        starredMoviesRecyclerView.adapter = null
        super.onDestroyView()
    }
}
