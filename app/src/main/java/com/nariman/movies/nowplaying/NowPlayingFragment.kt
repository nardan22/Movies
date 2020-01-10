package com.nariman.movies.nowplaying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.nariman.movies.RecyclerViewPagedListAdapter
import com.nariman.movies.databinding.NowPlayingFragmentBinding

class NowPlayingFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        activity!!.title = "Now playing movies"

        val viewModel = ViewModelProviders.of(this).get(NowPlayingViewModel::class.java)
        val binding = NowPlayingFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.nowPlayingViewModel = viewModel

        val adapter = RecyclerViewPagedListAdapter(RecyclerViewPagedListAdapter.OnItemClickListener{
            viewModel.onClickNavigation(it)
        })
        binding.nowPlayingRV.adapter = adapter
        viewModel.getNowPlayingMovies().observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.navigateToMovieDetails.observe(this, Observer {
            if (it != null){
                findNavController().navigate(
                    NowPlayingFragmentDirections.actionNowPlayingFragmentToMovieDetailFragment(it.id)
                )
                viewModel.onClickNavigationDone()
            }
        })

        return binding.root
    }
}
