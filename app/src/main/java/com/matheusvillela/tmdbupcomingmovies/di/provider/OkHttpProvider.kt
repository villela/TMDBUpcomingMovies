package com.matheusvillela.tmdbupcomingmovies.di.provider

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
    private val sleepInterceptor: SleepInterceptor
) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .addInterceptor(keyInterceptor)
                .addInterceptor(loggingInterceptor)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(sleepInterceptor)
        }
        return builder.build()
    }
}
