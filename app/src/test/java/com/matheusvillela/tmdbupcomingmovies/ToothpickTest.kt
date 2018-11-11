package com.matheusvillela.tmdbupcomingmovies

import android.app.Application
import com.matheusvillela.tmdbupcomingmovies.di.AppModule
import com.matheusvillela.tmdbupcomingmovies.repository.MovieRepository
import com.matheusvillela.tmdbupcomingmovies.repository.MovieRepositoryWebStrategy
import com.matheusvillela.tmdbupcomingmovies.shared.Api
import junit.framework.Assert.assertNotNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import toothpick.Scope
import toothpick.Toothpick


class ToothpickTest {
    private lateinit var appScope: Scope
    @After
    fun tearDown() {
        Toothpick.reset()
    }

    @Before
    fun setup() {
        appScope = Toothpick.openScope(this)
        val app: Application = Mockito.mock(Application::class.java)
        appScope.installModules(AppModule(app))
    }

    @Test
    fun testGetApiInstance() {
        val api = appScope.getInstance(Api::class.java)
        assertNotNull(api)
    }

    @Test
    fun testGetInterceptorInstance() {
        val interceptor = appScope.getInstance(HttpLoggingInterceptor::class.java)
        assertNotNull(interceptor)
    }

    @Test
    fun testGetOKHttpInstance() {
        val client = appScope.getInstance(OkHttpClient::class.java)
        assertNotNull(client)
    }

    @Test
    fun testGetMovieRepositoryWebStrategy() {
        val strategy = appScope.getInstance(MovieRepositoryWebStrategy::class.java)
        assertNotNull(strategy)
    }

    @Test
    fun testGetMovieRepository() {
        val repository = appScope.getInstance(MovieRepository::class.java)
        assertNotNull(repository)
    }
}