package com.matheusvillela.tmdbupcomingmovies.ui.shared

import com.matheusvillela.tmdbupcomingmovies.model.domain.Genre
import com.matheusvillela.tmdbupcomingmovies.model.domain.ImageConfiguration

class MovieConfiguration constructor(density: Int, config: ImageConfiguration, genres: List<Genre>) {
    val genres: Map<Int, String> = genres.associateBy({ it.id }, { it.name })
    val posterBaseUrl: String =
        config.secureBaseUrl + config.posterSizes[getIndexForDensity(config.posterSizes, density)]
    val backdropBaseUrl: String =
        config.secureBaseUrl + config.backdropSizes[getIndexForDensity(config.posterSizes, density)]

    private fun getIndexForDensity(list: List<String>, density: Int): Int {
        var index = density - 1
        if (index > list.size - 2) {
            index = list.size - 2 // 2: never use the original
        }
        if (index < 0) {
            index = 0
        }
        return index
    }
}