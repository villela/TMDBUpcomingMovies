package com.matheusvillela.tmdbupcomingmovies

import android.app.Application
import com.matheusvillela.tmdbupcomingmovies.di.AppModule
import com.matheusvillela.tmdbupcomingmovies.shared.Api
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import toothpick.Scope
import toothpick.Toothpick


class ApiTest {
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
    fun testUpcomingReturnsSomething() {
        val api: Api = appScope.getInstance(Api::class.java)
        api.getUpcomingMovies(1)
            .test()
            .assertValue { it.results.size > 0 }
    }
}