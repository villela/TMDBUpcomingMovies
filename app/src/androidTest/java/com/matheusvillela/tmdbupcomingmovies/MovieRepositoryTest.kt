package com.matheusvillela.tmdbupcomingmovies

import android.app.Application
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.matheusvillela.tmdbupcomingmovies.dao.MovieDao
import com.matheusvillela.tmdbupcomingmovies.di.AppModule
import com.matheusvillela.tmdbupcomingmovies.repository.movie.MovieRepository
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import toothpick.Scope
import toothpick.Toothpick

@RunWith(AndroidJUnit4ClassRunner::class)
class MovieRepositoryTest {
    private lateinit var appScope: Scope

    @After
    fun tearDown() {
        Toothpick.reset()
    }

    @Before
    fun setup() {
        appScope = Toothpick.openScope(this)
        val app: Application =InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application
        val appModule = AppModule(app)
        appModule.bind(AppDatabase::class.java).toInstance(
            Room.inMemoryDatabaseBuilder(app, AppDatabase::class.java).allowMainThreadQueries().build()
        )
        appScope.installModules(appModule)
    }

    @Test
    fun testGetMoviesReturnsSomething() {
        val repository = appScope.getInstance(MovieRepository::class.java)
        repository.getMovies(1)
            .observeOn(Schedulers.trampoline())
            .test()
            .assertValue { it.isNotEmpty() }
    }

    @Test
    fun testGetMoviesSavedOnDatabase() {
        val repository = appScope.getInstance(MovieRepository::class.java)
        val dao = appScope.getInstance(MovieDao::class.java)
        val values = repository.getMovies(1)
            .observeOn(Schedulers.trampoline())
            .test()
            .assertValue { it.isNotEmpty() }
            .values()
        val savedValues = dao.getAllByPage(1).test().values()
        val repoOrderedList = values[0].sortedBy { it.id }
        val savedOrderedList = savedValues[0].sortedBy { it.id }
        assertEquals(repoOrderedList, savedOrderedList)
    }
}
