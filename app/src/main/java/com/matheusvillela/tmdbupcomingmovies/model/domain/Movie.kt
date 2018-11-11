package com.matheusvillela.tmdbupcomingmovies.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String?,
    val genreIds: List<Int>,
    val releaseDate: String,
    val page: Int
)
