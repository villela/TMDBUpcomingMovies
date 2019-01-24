package com.matheusvillela.tmdbupcomingmovies.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.matheusvillela.tmdbupcomingmovies.R

class DetailsGenresAdapter(private val viewGroup: ViewGroup) {
    fun setGenres(genres: List<String>) {
        val inflater = LayoutInflater.from(viewGroup.context)
        viewGroup.removeAllViews()
        for (genre in genres) {
            val view = inflater.inflate(R.layout.item_genre, viewGroup, false)
            val textView = view.findViewById<TextView>(R.id.itemGenreText)
            textView.text = genre
            viewGroup.addView(view)
        }
    }
}