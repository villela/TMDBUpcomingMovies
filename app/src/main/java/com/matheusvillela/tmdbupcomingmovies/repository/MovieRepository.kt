package com.matheusvillela.tmdbupcomingmovies.repository

import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import io.reactivex.Single
import javax.inject.Inject

class MovieRepository @Inject constructor(private val webStrategy: MovieRepositoryWebStrategy) {
    fun getMovies(page: Int): Single<List<Movie>> {
        return webStrategy.getMovies(page)
    }
}