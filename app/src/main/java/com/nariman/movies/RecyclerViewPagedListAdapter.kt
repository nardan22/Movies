package com.nariman.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nariman.movies.model.Movie
import com.nariman.movies.util.MOVIE_POSTER_BASE_URL
import kotlinx.android.synthetic.main.movie_list_item.view.*

class RecyclerViewPagedListAdapter(val onClickListener: OnItemClickListener) :
    PagedListAdapter<Movie, RecyclerViewPagedListAdapter.MovieViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onClickListener.onClick(getItem(position)!!) }
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation)
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie?){
            itemView.movie_title.text = movie?.title
            itemView.movie_rating.text = movie?.voteAverage.toString()
            itemView.movie_release_date.text = movie?.releaseDate

            val moviePosterBaseUrl = MOVIE_POSTER_BASE_URL + movie?.posterPath
            Glide.with(itemView.context)
                .load(moviePosterBaseUrl)
                .into(itemView.movie_poster_image)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    class OnItemClickListener(val clickListener: (movie: Movie) -> Unit){
        fun onClick(movie: Movie){
            clickListener(movie)
        }
    }
}
