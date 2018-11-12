package com.matheusvillela.tmdbupcomingmovies

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.matheusvillela.tmdbupcomingmovies.dao.GenreDao
import com.matheusvillela.tmdbupcomingmovies.dao.MovieDao
import com.matheusvillela.tmdbupcomingmovies.model.domain.Genre
import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import com.matheusvillela.tmdbupcomingmovies.util.ListOfIntsConverter

@Database(entities = [Movie::class, Genre::class], version = 2)
@TypeConverters(value = [ListOfIntsConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
}