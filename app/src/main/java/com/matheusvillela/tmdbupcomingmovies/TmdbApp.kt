package com.matheusvillela.tmdbupcomingmovies

import android.app.Application
import timber.log.Timber

class TmdbApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}