package com.matheusvillela.tmdbupcomingmovies.model.api

import com.matheusvillela.tmdbupcomingmovies.model.domain.ImageConfiguration
import com.squareup.moshi.Json

class ApiImageConfiguration(
    @Json(name = "base_url")
    val baseUrl: String,
    @Json(name = "secure_base_url")
    val secureBaseUrl: String,
    @Json(name = "backdrop_sizes")
    val backdropSizes: List<String>,
    @Json(name = "poster_sizes")
    val posterSizes: List<String>
) {
    fun toImageConfiguration() = ImageConfiguration(1, baseUrl, secureBaseUrl, backdropSizes, posterSizes)
}