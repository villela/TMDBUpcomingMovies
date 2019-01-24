package com.matheusvillela.tmdbupcomingmovies.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.matheusvillela.tmdbupcomingmovies.model.domain.Genre
import io.reactivex.Single

@Dao
interface GenreDao {

    @Query("SELECT * FROM genre")
    fun getAll(): Single<List<Genre>>

    @Insert
    fun insertAll(genres: Iterable<Genre>)

    @Query("DELETE FROM genre")
    fun deleteAll()
}