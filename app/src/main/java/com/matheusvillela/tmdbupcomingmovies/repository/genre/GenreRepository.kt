package com.matheusvillela.tmdbupcomingmovies.repository.genre

import com.matheusvillela.tmdbupcomingmovies.dao.GenreDao
import com.matheusvillela.tmdbupcomingmovies.model.domain.Genre
import io.reactivex.Single
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val webStrategy: GenreRepositoryWebStrategy,
    private val sqliteStrategy: GenreRepositorySqliteStrategy,
    private val genreDao: GenreDao
) {
    fun getGenres(): Single<List<Genre>> {
        return sqliteStrategy.getGenres()
            .flatMap { it ->
                if (it.isEmpty()) {
                    webStrategy.getGenres()
                        .doAfterSuccess { genreDao.insertAll(it) }
                } else {
                    Single.just(it)
                }
            }
    }
}