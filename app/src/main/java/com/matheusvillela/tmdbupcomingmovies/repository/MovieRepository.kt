package com.matheusvillela.tmdbupcomingmovies.repository

import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import io.reactivex.Single
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val webStrategy: MovieRepositoryWebStrategy,
    private val sqliteStrategy: MovieRepositorySqliteStrategy
) {
    fun getMovies(page: Int): Single<List<Movie>> {
        return sqliteStrategy.getMovies(page)
            .flatMap { it ->
                if (it.isEmpty()) {
                    webStrategy.getMovies(page)
                } else {
                    Single.just(it)
                }
            }
    }
}