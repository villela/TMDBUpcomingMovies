package com.matheusvillela.tmdbupcomingmovies.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.matheusvillela.tmdbupcomingmovies.R
import com.matheusvillela.tmdbupcomingmovies.di.GlideApp
import com.matheusvillela.tmdbupcomingmovies.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var genresAdapter: DetailsGenresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movieId = intent?.extras?.getInt(EXTRAS_MOVIE_ID)
        if (movieId != null) {
            this.genresAdapter = DetailsGenresAdapter(activityDetailsGenresFlex)
            viewModel = obtainViewModel(DetailsViewModel::class.java)
            viewModel.data.observe(this, Observer {
                it?.let { data ->
                    activityDetailsName.text = data.movie.title
                    activityDetailsDescription.text = data.movie.overview
                    genresAdapter.setGenres(data.movie.getGenresList(data.configuration.genres))
                    activityDetailsRelease.text = resources.getString(R.string.release_date, data.movie.releaseDate)
                    if (data.movie.backdropPath != null) {
                        val url = data.configuration.backdropBaseUrl + data.movie.backdropPath
                        GlideApp.with(activityDetailsImage.context)
                            .load(url)
                            .centerCrop()
                            .placeholder(R.drawable.image_loading)
                            .error(R.drawable.ic_error_outline)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(activityDetailsImage)
                    } else {
                        activityDetailsImage.setImageResource(R.drawable.ic_not_interested)
                    }
                }
            })
            viewModel.start(movieId)
        } else {
            throw  Exception("movie id not passed")
        }
    }

    companion object {
        const val EXTRAS_MOVIE_ID = "extras_movie_id"
    }
}
