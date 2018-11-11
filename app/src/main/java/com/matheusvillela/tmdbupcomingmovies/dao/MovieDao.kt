package com.matheusvillela.tmdbupcomingmovies.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import io.reactivex.Single

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie WHERE page = :page")
    fun getAllByPage(page : Int): Single<List<Movie>>

    @Insert
    fun insertAll(movies : Iterable<Movie>)

    @Query("DELETE FROM movie")
    fun deleteAll()
}