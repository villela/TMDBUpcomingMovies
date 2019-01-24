package com.matheusvillela.tmdbupcomingmovies.repository.genre

import com.matheusvillela.tmdbupcomingmovies.model.domain.Genre
import io.reactivex.Single

interface GenreRepositoryStrategy {
    fun getGenres(): Single<List<Genre>>
}