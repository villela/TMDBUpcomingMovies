package com.matheusvillela.tmdbupcomingmovies.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.matheusvillela.tmdbupcomingmovies.dao.GenreDao
import com.matheusvillela.tmdbupcomingmovies.dao.ImageConfigurationDao
import com.matheusvillela.tmdbupcomingmovies.dao.MovieDao
import com.matheusvillela.tmdbupcomingmovies.model.domain.Genre
import com.matheusvillela.tmdbupcomingmovies.model.domain.ImageConfiguration
import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import com.matheusvillela.tmdbupcomingmovies.ui.shared.MovieConfiguration
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    app: Application,
    private val genreDao: GenreDao,
    private val movieDao: MovieDao,
    private val configurationDao: ImageConfigurationDao
) : AndroidViewModel(app) {
    val data: MutableLiveData<DetailsData> = MutableLiveData()
    private val density = app.resources.displayMetrics.density.toInt()
    private var disposable: Disposable? = null
    fun start(id: Int) {
        disposable = Single.zip(
            genreDao.getAll(),
            movieDao.getById(id),
            configurationDao.get().toSingle(),
            Function3<List<Genre>, Movie, ImageConfiguration,
                    DetailsData> { genres, movie, config ->
                DetailsData(movie, MovieConfiguration(density, config, genres))
            }
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                data.value = it
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}