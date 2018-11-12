package com.matheusvillela.tmdbupcomingmovies.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.matheusvillela.tmdbupcomingmovies.model.domain.ImageConfiguration
import io.reactivex.Maybe

@Dao
interface ImageConfigurationDao {

    @Query("SELECT * FROM imageConfiguration LIMIT 1")
    fun get(): Maybe<ImageConfiguration>

    @Insert
    fun insert(config: ImageConfiguration)

    @Query("DELETE FROM imageConfiguration")
    fun delete()
}