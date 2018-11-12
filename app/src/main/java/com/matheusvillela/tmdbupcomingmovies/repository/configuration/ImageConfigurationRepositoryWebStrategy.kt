package com.matheusvillela.tmdbupcomingmovies.repository.configuration

import com.matheusvillela.tmdbupcomingmovies.model.domain.ImageConfiguration
import com.matheusvillela.tmdbupcomingmovies.shared.Api
import io.reactivex.Single
import javax.inject.Inject

class ImageConfigurationRepositoryWebStrategy @Inject constructor(private val api: Api) {
    fun getConfig(): Single<ImageConfiguration> {
        return api.getConfiguration().flatMap { it ->
            Single.just(it.imageConfiguration.toImageConfiguration())
        }
    }
}