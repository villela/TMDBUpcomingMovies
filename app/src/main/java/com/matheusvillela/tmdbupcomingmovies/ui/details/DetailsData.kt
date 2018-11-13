package com.matheusvillela.tmdbupcomingmovies.ui.details

import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import com.matheusvillela.tmdbupcomingmovies.ui.shared.MovieConfiguration

data class DetailsData(
    val movie: Movie,
    val configuration: MovieConfiguration
)