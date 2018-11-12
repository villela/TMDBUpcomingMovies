package com.matheusvillela.tmdbupcomingmovies.repository.configuration

import com.matheusvillela.tmdbupcomingmovies.dao.ImageConfigurationDao
import com.matheusvillela.tmdbupcomingmovies.model.domain.ImageConfiguration
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class ImageConfigurationRepository @Inject constructor(
    private val webStrategy: ImageConfigurationRepositoryWebStrategy,
    private val sqliteStrategy: ImageConfigurationRepositorySqliteStrategy,
    private val dao: ImageConfigurationDao
) {
    fun getConfig(): Single<ImageConfiguration> {
        return sqliteStrategy.getConfig()
            .switchIfEmpty(Maybe.just(ImageConfiguration(-1, "", "", listOf(), listOf())))
            .toSingle()
            .flatMap {config ->
                if (config.id == -1) {
                    webStrategy.getConfig()
                        .doAfterSuccess { dao.insert(it) }
                } else {
                    Single.just(config)
                }
            }
    }
}