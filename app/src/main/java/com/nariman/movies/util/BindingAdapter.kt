package com.nariman.movies.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, url: String?){

    Glide.with(imageView.context)
        .load(MOVIE_POSTER_BASE_URL + url)
        .skipMemoryCache(true)
        .into(imageView)
}
