package com.matheusvillela.tmdbupcomingmovies.repository

import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import com.matheusvillela.tmdbupcomingmovies.shared.Api
import io.reactivex.Single
import javax.inject.Inject

class MovieRepositoryWebStrategy @Inject constructor(private val api: Api) : MovieRepositoryStrategy {
    override fun getMovies(page: Int): Single<List<Movie>> {
        return api.getUpcomingMovies(page).map { it.results }.map{ it.map { apiMovie -> apiMovie .toMovie(page) } }
    }
}