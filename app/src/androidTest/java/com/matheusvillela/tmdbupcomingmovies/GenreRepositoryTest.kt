package com.matheusvillela.tmdbupcomingmovies

import android.app.Application
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.matheusvillela.tmdbupcomingmovies.dao.GenreDao
import com.matheusvillela.tmdbupcomingmovies.di.AppModule
import com.matheusvillela.tmdbupcomingmovies.repository.genre.GenreRepository
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import toothpick.Scope
import toothpick.Toothpick

@RunWith(AndroidJUnit4ClassRunner::class)
class GenreRepositoryTest {
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
    fun testGetGenresReturnsSomething() {
        val repository = appScope.getInstance(GenreRepository::class.java)
        repository.getGenres()
            .observeOn(Schedulers.trampoline())
            .test()
            .assertValue { it.isNotEmpty() }
    }

    @Test
    fun testGetGenresSavedOnDatabase() {
        val repository = appScope.getInstance(GenreRepository::class.java)
        val dao = appScope.getInstance(GenreDao::class.java)
        val values = repository.getGenres()
            .observeOn(Schedulers.trampoline())
            .test()
            .assertValue { it.isNotEmpty() }
            .values()
        val savedValues = dao.getAll().test().values()
        val repoOrderedList = values[0].sortedBy { it.id }
        val savedOrderedList = savedValues[0].sortedBy { it.id }
        assertEquals(repoOrderedList, savedOrderedList)
    }
}
