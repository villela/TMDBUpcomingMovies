package com.matheusvillela.tmdbupcomingmovies.model.api

import com.squareup.moshi.Json

class ApiGenreResponse(
    @Json(name = "genres")
    val genres: List<ApiGenre>
)