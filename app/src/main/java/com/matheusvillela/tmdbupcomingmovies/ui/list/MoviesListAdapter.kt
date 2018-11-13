package com.matheusvillela.tmdbupcomingmovies.ui.list

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jakewharton.rxbinding3.view.clicks
import com.matheusvillela.tmdbupcomingmovies.R
import com.matheusvillela.tmdbupcomingmovies.di.GlideApp
import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import com.matheusvillela.tmdbupcomingmovies.ui.shared.MovieConfiguration
import kotlinx.android.synthetic.main.item_movie.view.*
import timber.log.Timber
import java.util.concurrent.TimeUnit


class MoviesListAdapter(private val onLastItemVisibleListener: OnLastItemVisibleListener,
                        private val onMovieClickedListener: OnMovieClickedListener,
                        private val configuration: MovieConfiguration
)
    : RecyclerView.Adapter<MoviesListAdapter.MoviesAdapterHolder>() {

    private var itemWidth: Int? = null
    private var defaultReleaseTextColor: Int? = null

    interface OnLastItemVisibleListener {
        fun onLastItemVisible()
    }

    interface OnMovieClickedListener {
        fun onMovieClicked(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        val moviesAdapterHolder = MoviesAdapterHolder(view)
        if (itemWidth == null) {
            val wm = parent.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)
            itemWidth = if (size.x < size.y) {
                size.x
            } else {
                size.x / 2 - 1
            }
            Timber.d("itemWidth set to %s", itemWidth)
        }
        itemWidth?.let { view.layoutParams.width = it }
        return moviesAdapterHolder
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MoviesAdapterHolder, position: Int) {
        val movie = movies[position]
        holder.name.text = (movie.title)
        holder.release.text = holder.release.resources.getString(R.string.release_date, movie.releaseDate)
        if (position == movies.size - 1) {
            onLastItemVisibleListener.onLastItemVisible()
        }
        holder.itemView.setOnClickListener { onMovieClickedListener.onMovieClicked(movie) }
        holder.itemView.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe{onMovieClickedListener.onMovieClicked(movie)}
        if (defaultReleaseTextColor == null) {
            defaultReleaseTextColor = holder.release.currentTextColor
        }
        holder.release.setTextColor(
                if (movie.releaseDate.contains("2017")) {
                    ContextCompat.getColor(holder.itemView.context, R.color.highlight_red)
                } else {
                    defaultReleaseTextColor ?: Color.WHITE // fallback, should not happen
                })
        movie.posterPath?.let {
            setPosterImage(holder.image, it)
        }
    }

    private fun setPosterImage(imageView: ImageView, imagePath: String?) {
        if (imagePath != null) {
            GlideApp.with(imageView.context)
                    .load(configuration.posterBaseUrl + imagePath)
                    .centerCrop()
                    .placeholder(R.drawable.image_loading)
                    .error(R.drawable.ic_error_outline)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)
        } else {
            imageView.setImageResource(R.drawable.ic_not_interested)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MoviesAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.item_movie_name
        val release: TextView = itemView.item_movie_release
        val image: ImageView = itemView.item_movie_image
    }

    private var movies: List<Movie> = listOf()

    fun setMovies(it: List<Movie>) {
        this.movies = it
        notifyDataSetChanged()
    }}
