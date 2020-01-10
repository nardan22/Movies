package com.nariman.movies.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nariman.movies.R

@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, url: String?){

    if (url != null) {
        Glide.with(imageView.context)
            .load(MOVIE_POSTER_BASE_URL + url)
            .skipMemoryCache(true)
            .into(imageView)
    }
    else {
        imageView.setImageResource(R.drawable.noimage)
    }
}
