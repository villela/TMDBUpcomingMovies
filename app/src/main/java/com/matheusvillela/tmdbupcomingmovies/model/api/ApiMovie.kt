package com.matheusvillela.tmdbupcomingmovies.model.api

import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import com.squareup.moshi.Json

class ApiMovie(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "release_date")
    val releaseDate: String
) {
    fun toMovie(page: Int): Movie {
        return Movie(id, title, posterPath, backdropPath, overview, genreIds, releaseDate, page)
    }
}