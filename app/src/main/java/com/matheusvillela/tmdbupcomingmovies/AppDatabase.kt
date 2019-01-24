package com.matheusvillela.tmdbupcomingmovies

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.matheusvillela.tmdbupcomingmovies.dao.GenreDao
import com.matheusvillela.tmdbupcomingmovies.dao.ImageConfigurationDao
import com.matheusvillela.tmdbupcomingmovies.dao.MovieDao
import com.matheusvillela.tmdbupcomingmovies.model.domain.Genre
import com.matheusvillela.tmdbupcomingmovies.model.domain.ImageConfiguration
import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import com.matheusvillela.tmdbupcomingmovies.util.ListOfIntsConverter
import com.matheusvillela.tmdbupcomingmovies.util.ListOfStringsConverter

@Database(entities = [Movie::class, Genre::class, ImageConfiguration::class], version = 3)
@TypeConverters(value = [ListOfIntsConverter::class, ListOfStringsConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun imageConfigurationDao(): ImageConfigurationDao
}