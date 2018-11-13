package com.matheusvillela.tmdbupcomingmovies.di.provider

import com.matheusvillela.tmdbupcomingmovies.AppDatabase
import com.matheusvillela.tmdbupcomingmovies.dao.ImageConfigurationDao
import javax.inject.Inject
import javax.inject.Provider

class ImageConfigurationDaoProvider @Inject constructor(private val appDatabase: AppDatabase) : Provider<ImageConfigurationDao> {
    override fun get(): ImageConfigurationDao {
        return appDatabase.imageConfigurationDao()
    }
}