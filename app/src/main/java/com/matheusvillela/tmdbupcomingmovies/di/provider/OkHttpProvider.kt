package com.matheusvillela.tmdbupcomingmovies.di.provider

import android.app.Application
import com.matheusvillela.tmdbupcomingmovies.BuildConfig
import com.matheusvillela.tmdbupcomingmovies.util.SleepInterceptor
import com.matheusvillela.tmdbupcomingmovies.util.TmdbKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Provider


class OkHttpProvider @Inject constructor(
    private val loggingInterceptor: HttpLoggingInterceptor,
    private val keyInterceptor: TmdbKeyInterceptor,
    private val sleepInterceptor: SleepInterceptor,
    private val application: Application
) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .addInterceptor(keyInterceptor)
                .addInterceptor(loggingInterceptor)
                //.cache(Cache(File(application.cacheDir, "http"), 1 * 1024 * 1024))
        if (BuildConfig.DEBUG) {
            //builder.addInterceptor(sleepInterceptor)
        }
        return builder.build()
    }
}
