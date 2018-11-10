package com.matheusvillela.tmdbupcomingmovies.model.api

import com.squareup.moshi.Json

class ApiMovie(
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
)