package com.matheusvillela.tmdbupcomingmovies.model.domain

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String?,
    val genreIds: List<Int>,
    val releaseDate: String,
    val page: Int
)