package com.nariman.movies.castdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nariman.movies.R
import com.nariman.movies.model.castinfo.CastMovieInfo
import com.nariman.movies.util.MOVIE_POSTER_BASE_URL
import kotlinx.android.synthetic.main.horizontal_movie_list_item.view.*

class CastMoviesRecyclerViewAdapter (val onClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CastMoviesRecyclerViewAdapter.CastMovieViewHolder>() {

    private var castMovies: List<CastMovieInfo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastMovieViewHolder {
        return CastMovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.horizontal_movie_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if(castMovies != null) castMovies!!.size else 0
    }

    override fun onBindViewHolder(holder: CastMovieViewHolder, position: Int) {
        holder.bind(castMovies!![position])
        holder.itemView.setOnClickListener{ onClickListener.onClick(castMovies!![position]) }
    }

    fun setCastMovies(castMoviesList: List<CastMovieInfo>){
        castMovies = castMoviesList
        notifyDataSetChanged()
    }


    class CastMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(castMovieInfo: CastMovieInfo) {
            itemView.movie_title.text = castMovieInfo.title
            itemView.movie_release_date.text = castMovieInfo.releaseDate
            itemView.movie_rating.text = castMovieInfo.voteAverage.toString()
            if (castMovieInfo.posterPath != null){
                Glide.with(itemView.context)
                    .load(MOVIE_POSTER_BASE_URL + castMovieInfo.posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(itemView.movie_poster_image)
            }
            else {
                itemView.movie_poster_image.setImageResource(R.drawable.noimage)
            }
        }
    }

    class OnItemClickListener(val clickListener: (castMovieInfo: CastMovieInfo) -> Unit){
        fun onClick(castMovieInfo: CastMovieInfo){ clickListener(castMovieInfo)}
    }
}