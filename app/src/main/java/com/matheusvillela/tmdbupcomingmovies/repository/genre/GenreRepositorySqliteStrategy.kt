package com.matheusvillela.tmdbupcomingmovies.repository.genre

import com.matheusvillela.tmdbupcomingmovies.dao.GenreDao
import com.matheusvillela.tmdbupcomingmovies.model.domain.Genre
import io.reactivex.Single
import javax.inject.Inject

class GenreRepositorySqliteStrategy @Inject constructor(private val genreDao: GenreDao) : GenreRepositoryStrategy {
    override fun getGenres(): Single<List<Genre>> {
        return genreDao.getAll()
    }
}