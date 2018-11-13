package com.matheusvillela.tmdbupcomingmovies.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import timber.log.Timber

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
) {
    fun getGenresList(genres: Map<Int, String>?): List<String> {
        val result = arrayListOf<String>()
        if (genres != null) {
            for (id in genreIds) {
                val genre = genres[id]
                if (genre == null) {
                    Timber.e("no gender found for id: %s", id)
                } else {
                    result.add(genre)
                }
            }
        }
        return result
    }
}
