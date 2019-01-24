package com.matheusvillela.tmdbupcomingmovies.repository.genre

import com.matheusvillela.tmdbupcomingmovies.model.domain.Genre
import com.matheusvillela.tmdbupcomingmovies.shared.Api
import io.reactivex.Single
import javax.inject.Inject

class GenreRepositoryWebStrategy @Inject constructor(private val api: Api) : GenreRepositoryStrategy {
    override fun getGenres(): Single<List<Genre>> {
        return api.getGenres().map { it.genres }.map { it.map { apiGenre -> apiGenre.toGenre() } }
    }
}