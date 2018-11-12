package com.matheusvillela.tmdbupcomingmovies

import android.app.Application
import com.matheusvillela.tmdbupcomingmovies.di.AppModule
import com.matheusvillela.tmdbupcomingmovies.repository.genre.GenreRepositoryWebStrategy
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import toothpick.Toothpick


class GenreRepositoryWebStrategyTest {

    private lateinit var strategy: GenreRepositoryWebStrategy

    @After
    fun tearDown() {
        Toothpick.reset()
    }

    @Before
    fun setup() {
        val appScope = Toothpick.openScope(this)
        val app: Application = Mockito.mock(Application::class.java)
        appScope.installModules(AppModule(app))
        strategy = appScope.getInstance(GenreRepositoryWebStrategy::class.java)
    }

    @Test
    fun testGetGenresReturnsSomething() {
        strategy.getGenres()
            .test()
            .assertValue { it.isNotEmpty() }
    }

    @Test
    fun testGetReturnsSameStuff() {
        val firstGenres = strategy.getGenres()
            .test().values()
        val secondGenres = strategy.getGenres()
            .test().values()
        assertEquals(firstGenres, secondGenres)
    }
}