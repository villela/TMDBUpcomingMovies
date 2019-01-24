package com.matheusvillela.tmdbupcomingmovies.di.provider

import com.matheusvillela.tmdbupcomingmovies.AppDatabase
import com.matheusvillela.tmdbupcomingmovies.dao.GenreDao
import javax.inject.Inject
import javax.inject.Provider

class GenreDaoProvider @Inject constructor(private val appDatabase: AppDatabase) : Provider<GenreDao> {
    override fun get(): GenreDao {
        return appDatabase.genreDao()
    }
}