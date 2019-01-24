package com.matheusvillela.tmdbupcomingmovies.model.api

import com.squareup.moshi.Json

class ApiImageConfigurationResponse(
    @Json(name = "images")
    val imageConfiguration: ApiImageConfiguration
)