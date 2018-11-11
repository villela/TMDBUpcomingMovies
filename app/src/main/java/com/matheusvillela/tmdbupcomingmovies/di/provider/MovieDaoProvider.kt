package com.matheusvillela.tmdbupcomingmovies.di.provider

import com.matheusvillela.tmdbupcomingmovies.AppDatabase
import com.matheusvillela.tmdbupcomingmovies.dao.MovieDao
import javax.inject.Inject
import javax.inject.Provider

class MovieDaoProvider @Inject constructor(private val appDatabase: AppDatabase) : Provider<MovieDao> {
    override fun get(): MovieDao {
        return appDatabase.movieDao()
    }
}