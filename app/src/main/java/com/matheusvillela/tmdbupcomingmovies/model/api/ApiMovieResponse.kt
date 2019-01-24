package com.matheusvillela.tmdbupcomingmovies.model.api

import com.squareup.moshi.Json

class ApiMovieResponse(
    @Json(name = "results")
    val results: MutableList<ApiMovie>,
    @Json(name = "page")
    val page: Int,
    @Json(name = "total_results")
    val totalResults: Int,
    @Json(name = "total_pages")
    val totalPages: Int
)