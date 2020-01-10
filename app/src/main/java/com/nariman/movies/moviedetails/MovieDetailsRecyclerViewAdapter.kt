package com.nariman.movies.moviedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nariman.movies.R
import com.nariman.movies.model.actors.Cast
import com.nariman.movies.util.MOVIE_POSTER_BASE_URL
import kotlinx.android.synthetic.main.movie_actors_list_item.view.*

class MovieDetailsRecyclerViewAdapter(val onClickListener: OnItemClickListener): RecyclerView.Adapter<MovieDetailsRecyclerViewAdapter.ActorViewHolder>() {

    private var actors: List<Cast>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_actors_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int = actors?.size ?: 0

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(actors!!.get(position))
        holder.itemView.setOnClickListener { onClickListener.onClick(actors!!.get(position)) }
    }


    fun setActors(actorsList: List<Cast>){
        actors = actorsList
        notifyDataSetChanged()
    }


    inner class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(actor: Cast) {
            itemView.actor_name.text = actor.name
            itemView.actor_character.text = actor.character
            Glide.with(itemView.context)
                .load("$MOVIE_POSTER_BASE_URL${actor.profilePath}")
                .skipMemoryCache(true)
                .into(itemView.actor_image)
        }
    }


    class OnItemClickListener(val clickListener: (actor: Cast) -> Unit) {
        fun onClick(actor: Cast){
            clickListener(actor)
        }
    }
}