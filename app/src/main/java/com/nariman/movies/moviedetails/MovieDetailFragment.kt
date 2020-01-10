package com.nariman.movies.moviedetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.nariman.movies.RecyclerViewPagedListAdapter
import com.nariman.movies.databinding.MovieDetailsFragmentBinding
import kotlinx.android.synthetic.main.movie_details_fragment.*

class MovieDetailFragment : Fragment() {

    private lateinit var vm: MovieDetailViewModel
    private var mWebView: WebView? = null
    private var mWebContainer: LinearLayout? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = MovieDetailsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val movieId = MovieDetailFragmentArgs.fromBundle(arguments!!).movieId

        mWebView = binding.webView
        mWebContainer = binding.webContainer

        Log.i("someerror", "Movie ID: $movieId")

        val viewModelFactory = MovieDetailViewModelFactory(activity!!.application, movieId)
        val movieDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MovieDetailViewModel::class.java)
        binding.movieDetailViewModel = movieDetailViewModel

        vm = movieDetailViewModel

        movieDetailViewModel.movieVideos.observe(this, Observer {
            movieDetailViewModel.setMovieVideoInfo()
        })

        // Set adapter
        val actorsAdapter = MovieDetailsRecyclerViewAdapter(MovieDetailsRecyclerViewAdapter.OnItemClickListener{
            //  Set on click listener of item to navigate actor details
            movieDetailViewModel.onClickNavigationToActorDetail(it)
        })
        // Set adapter to Actor's RecyclerView
        binding.movieActorsRecyclerView.adapter = actorsAdapter
        movieDetailViewModel.actorsList.observe(this, Observer {
            actorsAdapter.setActors(it)
        })
        movieDetailViewModel.navigateToActorDetails.observe(this, Observer { actor ->
            if (actor != null) {
                Toast.makeText(context, "Navigation Done", Toast.LENGTH_LONG).show()
                findNavController().navigate(
                    MovieDetailFragmentDirections.actionMovieDetailFragmentToCastDetailsFragment(actor.id)
                )
                movieDetailViewModel.onNavigationToActorDetailsDone()
            }
        })

        movieDetailViewModel.movieVideoInfo.observe(this, Observer {
            //Log.i("someerror", "Movie Video Info: ${it.toString()}")
            if (it.key != null){
                val frameVideo = "<html><body style=\"margin:0;padding:0;\"><iframe width=\"100%\" height=\"100%\" " +
                        "src=\"https://www.youtube.com/embed/${it.key}\" " +
                        "frameborder=\"0\" allowfulscreen padding=\"0\">" +
                        "</iframe></html></body>"

                mWebView!!.webChromeClient = WebChromeClient()
                val webSettings = mWebView!!.settings
                webSettings.javaScriptEnabled = true
                webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
                webSettings.setAppCacheEnabled(false)
                mWebView!!.loadData(frameVideo, "text/html", "utf-8")
                mWebView!!.pauseTimers()
                mWebView!!.resumeTimers()
            }
        })

        val relatedRecyclerViewAdapter =
            RecyclerViewPagedListAdapter(
                RecyclerViewPagedListAdapter.OnItemClickListener{
            movieDetailViewModel.onClickNavigationToRelatedMovieDetails(it)
        }, "horizontal"
            )
        binding.relatedMoviesRecyclerView.adapter = relatedRecyclerViewAdapter

        movieDetailViewModel.navigateToRelatedMovieDetails.observe(this, Observer{
            if (it != null){
                findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentSelf(it.id))
                movieDetailViewModel.onNavigationToRelatedMovieDetailsDone()
            }
        })

        movieDetailViewModel.getRelatedMoviesPagedList().observe(this, Observer {
            relatedRecyclerViewAdapter.submitList(it)
        })

        return binding.root
    }

    override fun onDestroyView() {

        Log.i("myfragments", "MovieDetailFragment.onDestroyView()")

        if (mWebView != null){
            mWebView!!.webChromeClient = null
            mWebView!!.settings.javaScriptEnabled = false
            mWebView!!.removeAllViews()
            mWebView!!.clearHistory()
            mWebView!!.clearCache(true)
            mWebView!!.clearFormData()
            mWebView!!.destroy()
            mWebContainer!!.removeAllViews()
            mWebView = null
            mWebContainer = null
        }
        movieActorsRecyclerView.adapter = null
        relatedMoviesRecyclerView.adapter = null

        Log.i("myfragments", "${relatedMoviesRecyclerView.adapter}")

        System.gc()
        System.gc()
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.i("myfragments", "MovieDetailFragment.onDestroy()")
        super.onDestroy()
    }
}