package com.nariman.movies.upcoming

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.nariman.movies.RecyclerViewPagedListAdapter
import com.nariman.movies.databinding.UpcomingFragmentBinding
import kotlinx.android.synthetic.main.upcoming_fragment.*

class UpcomingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity!!.title = "Upcoming Movies"

        val upcomingViewModel = ViewModelProviders.of(this).get(UpcomingViewModel::class.java)

        val binding = UpcomingFragmentBinding.inflate(inflater)
        binding.upcomingViewModel = upcomingViewModel

        val adapter = RecyclerViewPagedListAdapter(
            RecyclerViewPagedListAdapter.OnItemClickListener{
                upcomingViewModel.onClickNavigation(it)
            }
        )

        binding.rvUpcoming.adapter = adapter

        upcomingViewModel.getUpcomingMoviesPagedList().observe(this, Observer {
            adapter.submitList(it)
        })

        upcomingViewModel.navigateToMovieDetails.observe(this, Observer {
            if (it != null){
                findNavController().navigate(UpcomingFragmentDirections.actionUpcomingFragmentToMovieDetailFragment(it.id))
                upcomingViewModel.onNavigationDone()
            }
        })

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        rvUpcoming.adapter = null
        super.onDestroyView()
    }
}
