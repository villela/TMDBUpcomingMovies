package com.matheusvillela.tmdbupcomingmovies.repository.movie

import com.matheusvillela.tmdbupcomingmovies.dao.MovieDao
import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import io.reactivex.Single
import javax.inject.Inject

class MovieRepositorySqliteStrategy @Inject constructor(private val movieDao: MovieDao) :
    MovieRepositoryStrategy {
    override fun getMovies(page: Int): Single<List<Movie>> {
        return movieDao.getAllByPage(page)
    }
}