package com.matheusvillela.tmdbupcomingmovies.shared

import com.matheusvillela.tmdbupcomingmovies.model.api.ApiGenreResponse
import com.matheusvillela.tmdbupcomingmovies.model.api.ApiImageConfigurationResponse
import com.matheusvillela.tmdbupcomingmovies.model.api.ApiMovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("page") currentPage: Int): Single<ApiMovieResponse>

    @GET("genre/movie/list")
    fun getGenres(): Single<ApiGenreResponse>

    @GET("configuration")
    fun getConfiguration(): Single<ApiImageConfigurationResponse>
}
