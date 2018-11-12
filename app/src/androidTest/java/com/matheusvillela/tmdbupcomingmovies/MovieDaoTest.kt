package com.matheusvillela.tmdbupcomingmovies

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import toothpick.Toothpick

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var db: AppDatabase

    @After
    fun tearDown() {
        Toothpick.reset()
    }

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, AppDatabase::class.java).allowMainThreadQueries().build()
    }

    @Test
    fun testDaoInsert() {
        val dao = db.movieDao()
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
