package com.matheusvillela.tmdbupcomingmovies

import android.app.Application
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.matheusvillela.tmdbupcomingmovies.dao.MovieDao
import com.matheusvillela.tmdbupcomingmovies.di.AppModule
import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import toothpick.Scope
import toothpick.Toothpick

@RunWith(AndroidJUnit4ClassRunner::class)
class MovieDaoTest {

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
    fun testDaoInsert() {
        val dao = appScope.getInstance(MovieDao::class.java)
        val movie = Movie(5, "title", null, null, "Overview",
            listOf(), "2018/08/08", 1)
        dao.insertAll(listOf(movie))
        val all = dao.getAllByPage(movie.page).test()
        assertNotNull(all)
        all.assertValue { !it.isEmpty() }
        all.assertValue { list -> list.filter { it.id == movie.id }.size == 1 }
        all.assertValue { list -> list.first { it.id == movie.id } == movie }
    }
}
