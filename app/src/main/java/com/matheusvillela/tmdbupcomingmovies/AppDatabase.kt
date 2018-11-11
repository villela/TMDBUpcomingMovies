package com.matheusvillela.tmdbupcomingmovies

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.matheusvillela.tmdbupcomingmovies.dao.MovieDao
import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import com.matheusvillela.tmdbupcomingmovies.util.ListOfIntsConverter

@Database(entities = [Movie::class], version = 1)
@TypeConverters(value = [ListOfIntsConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}