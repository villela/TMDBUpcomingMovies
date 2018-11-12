package com.matheusvillela.tmdbupcomingmovies.repository.movie

import com.matheusvillela.tmdbupcomingmovies.dao.MovieDao
import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import io.reactivex.Single
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val webStrategy: MovieRepositoryWebStrategy,
    private val sqliteStrategy: MovieRepositorySqliteStrategy,
    private val movieDao: MovieDao
) {
    fun getMovies(page: Int): Single<List<Movie>> {
        return sqliteStrategy.getMovies(page)
            .flatMap { it ->
                if (it.isEmpty()) {
                    webStrategy.getMovies(page)
                        .doAfterSuccess { movieDao.insertAll(it) }
                } else {
                    Single.just(it)
                }
            }
    }
}