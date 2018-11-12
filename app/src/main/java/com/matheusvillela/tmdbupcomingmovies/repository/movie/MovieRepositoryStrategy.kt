package com.matheusvillela.tmdbupcomingmovies.repository.movie

import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import io.reactivex.Single

interface MovieRepositoryStrategy {
    fun getMovies(page: Int): Single<List<Movie>>
}