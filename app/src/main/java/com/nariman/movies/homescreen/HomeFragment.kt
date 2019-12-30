package com.nariman.movies.homescreen

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nariman.movies.databinding.HomeFragmentBinding
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = HomeFragmentBinding.inflate(inflater)


        if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 2)
        else
            binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 3)


        val viewModel = ViewModelProviders.of(this, HomeViewModelFactory()).get(HomeViewModel::class.java)
        binding.homeViewModel = viewModel


        val adapter =
            RecyclerViewPagedListAdapter(RecyclerViewPagedListAdapter.OnItemClickListener {
                viewModel.onClickNavigation(it)
            })

        binding.moviesRecyclerView.adapter = adapter

        viewModel.getMoviesPagedList().observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.navigateToMovieDetails.observe(this, Observer {
            if (it != null){
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(it.id))
                viewModel.navigationDone()
            }
        })

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()

        // Unbind adapter to avoid memory leak
        if (moviesRecyclerView != null) moviesRecyclerView.adapter = null
    }
}