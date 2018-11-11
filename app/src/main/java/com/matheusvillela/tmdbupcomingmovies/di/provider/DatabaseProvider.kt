package com.matheusvillela.tmdbupcomingmovies.di.provider

import android.app.Application
import androidx.room.Room
import com.matheusvillela.tmdbupcomingmovies.AppDatabase
import javax.inject.Inject
import javax.inject.Provider

class DatabaseProvider @Inject constructor(private val app: Application) : Provider<AppDatabase> {
    override fun get(): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java, "tmdb"
        ).allowMainThreadQueries()
            .build()
    }
}