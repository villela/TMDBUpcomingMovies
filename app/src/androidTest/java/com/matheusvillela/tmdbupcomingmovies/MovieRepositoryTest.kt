package com.matheusvillela.tmdbupcomingmovies

import android.app.Application
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.matheusvillela.tmdbupcomingmovies.di.AppModule
import com.matheusvillela.tmdbupcomingmovies.repository.MovieRepository
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import toothpick.Scope
import toothpick.Toothpick

@RunWith(AndroidJUnit4::class)
class MovieRepositoryTest {
    private lateinit var appScope: Scope

    @After
    fun tearDown() {
        Toothpick.reset()
    }

    @Before
    fun setup() {
        appScope = Toothpick.openScope(this)
        val app: Application = Mockito.mock(TmdbApp::class.java)
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
}
