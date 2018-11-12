package com.matheusvillela.tmdbupcomingmovies

import android.app.Application
import com.matheusvillela.tmdbupcomingmovies.di.AppModule
import com.matheusvillela.tmdbupcomingmovies.repository.movie.MovieRepositoryWebStrategy
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException
import toothpick.Toothpick


class MovieRepositoryWebStrategyTest {

    private lateinit var strategy: MovieRepositoryWebStrategy

    @After
    fun tearDown() {
        Toothpick.reset()
    }

    @Before
    fun setup() {
        val appScope = Toothpick.openScope(this)
        val app: Application = Mockito.mock(Application::class.java)
        appScope.installModules(AppModule(app))
        strategy = appScope.getInstance(MovieRepositoryWebStrategy::class.java)
    }

    @Test
    fun testGetMoviesReturnsSomething() {
        strategy.getMovies(1)
            .test()
            .assertValue { it.isNotEmpty() }
    }

    @Test
    fun testGetWrongPageResultsError() {
        strategy.getMovies(1234424)
            .test()
            .assertError(HttpException::class.java)
    }

    @Test
    fun testGetSamePageReturnSameMovies() {
        val firstMovies = strategy.getMovies(1)
            .test().values()
        val secondMovies = strategy.getMovies(1)
            .test().values()
        assertEquals(firstMovies, secondMovies)
    }

    @Test
    fun testGetDifferentPageReturnDifferentMovies() {
        val firstMovies = strategy.getMovies(1)
            .test().values()
        val secondMovies = strategy.getMovies(2)
            .test().values()
        assertNotEquals(firstMovies, secondMovies)
    }
}