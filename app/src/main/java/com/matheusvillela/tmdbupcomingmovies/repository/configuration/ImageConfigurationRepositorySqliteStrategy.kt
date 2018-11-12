package com.matheusvillela.tmdbupcomingmovies.repository.configuration

import com.matheusvillela.tmdbupcomingmovies.dao.ImageConfigurationDao
import com.matheusvillela.tmdbupcomingmovies.model.domain.ImageConfiguration
import io.reactivex.Maybe
import javax.inject.Inject

class ImageConfigurationRepositorySqliteStrategy @Inject constructor
    (private val configurationDao: ImageConfigurationDao) {
    fun getConfig(): Maybe<ImageConfiguration> {
        return configurationDao.get()
    }
}