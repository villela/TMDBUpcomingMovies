package com.matheusvillela.tmdbupcomingmovies.model.api

import com.matheusvillela.tmdbupcomingmovies.model.domain.Genre
import com.squareup.moshi.Json

class ApiGenre(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
) {
    fun toGenre() = Genre(id, name)
}