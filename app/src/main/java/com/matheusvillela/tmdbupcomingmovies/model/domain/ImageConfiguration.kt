package com.matheusvillela.tmdbupcomingmovies.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageConfiguration(
    @PrimaryKey val id: Int,
    val baseUrl: String,
    val secureBaseUrl: String,
    val backdropSizes: List<String>,
    val posterSizes: List<String>
)