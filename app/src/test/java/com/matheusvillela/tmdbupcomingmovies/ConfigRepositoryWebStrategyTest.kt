package com.matheusvillela.tmdbupcomingmovies

import android.app.Application
import com.matheusvillela.tmdbupcomingmovies.di.AppModule
import com.matheusvillela.tmdbupcomingmovies.repository.configuration.ImageConfigurationRepositoryWebStrategy
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import toothpick.Toothpick


class ConfigRepositoryWebStrategyTest {

    private lateinit var strategy: ImageConfigurationRepositoryWebStrategy

    @After
    fun tearDown() {
        Toothpick.reset()
    }

    @Before
    fun setup() {
        val appScope = Toothpick.openScope(this)
        val app: Application = Mockito.mock(Application::class.java)
        appScope.installModules(AppModule(app))
        strategy = appScope.getInstance(ImageConfigurationRepositoryWebStrategy::class.java)
    }

    @Test
    fun testGetConfigReturnsSomething() {
        strategy.getConfig()
            .test()
            .assertNoErrors()
    }

    @Test
    fun testGetReturnsSameStuff() {
        val first = strategy.getConfig()
            .test().values()
        val second = strategy.getConfig()
            .test().values()
        assertEquals(first, second)
    }
}