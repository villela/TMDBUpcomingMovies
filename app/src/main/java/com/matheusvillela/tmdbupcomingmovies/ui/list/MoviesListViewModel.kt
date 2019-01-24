package com.matheusvillela.tmdbupcomingmovies.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.matheusvillela.tmdbupcomingmovies.R
import com.matheusvillela.tmdbupcomingmovies.dao.GenreDao
import com.matheusvillela.tmdbupcomingmovies.dao.ImageConfigurationDao
import com.matheusvillela.tmdbupcomingmovies.dao.MovieDao
import com.matheusvillela.tmdbupcomingmovies.model.domain.Genre
import com.matheusvillela.tmdbupcomingmovies.model.domain.ImageConfiguration
import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import com.matheusvillela.tmdbupcomingmovies.repository.configuration.ImageConfigurationRepository
import com.matheusvillela.tmdbupcomingmovies.repository.genre.GenreRepository
import com.matheusvillela.tmdbupcomingmovies.repository.movie.MovieRepository
import com.matheusvillela.tmdbupcomingmovies.shared.LoadingStatus
import com.matheusvillela.tmdbupcomingmovies.ui.shared.MovieConfiguration
import com.matheusvillela.tmdbupcomingmovies.util.SingleLiveEvent
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(
    app: Application,
    private val configurationRepository: ImageConfigurationRepository,
    private val genreRepository: GenreRepository,
    private val movieRepository: MovieRepository,
    private val genreDao: GenreDao,
    private val movieDao: MovieDao,
    private val configurationDao: ImageConfigurationDao

) : AndroidViewModel(app) {
    val movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val viewStatus: MutableLiveData<LoadingStatus> = MutableLiveData()
    val configuration: MutableLiveData<MovieConfiguration> = MutableLiveData()
    var pageLoadingError: SingleLiveEvent<String> = SingleLiveEvent()
    private val disposables = CompositeDisposable()
    private var currentPage = 0
    private val density = app.resources.displayMetrics.density.toInt()
    private var lastPageReached = false

    init {
        movies.value = listOf()
    }

    fun start() {
        if (currentPage == 0) {
            viewStatus.value = LoadingStatus.LOADING
            disposables.add(Single.zip(
                configurationRepository.getConfig(),
                genreRepository.getGenres(),
                BiFunction<ImageConfiguration, List<Genre>, MovieConfiguration> { config, genres ->
                    MovieConfiguration(density, config, genres)
                }
            ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ config ->
                    viewStatus.value = LoadingStatus.READY
                    configuration.value = config
                    loadNextPage()
                }, { it ->
                    Timber.e(it)
                    viewStatus.value = LoadingStatus.ERROR
                })
            )
        }
    }

    fun loadNextPage() {
        if (!lastPageReached && viewStatus.value != LoadingStatus.LOADING) {
            viewStatus.value = LoadingStatus.LOADING
            disposables.add(
                movieRepository.getMovies(currentPage + 1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        if (it?.size ?: 0 > 0) {
                            val list = mutableListOf<Movie>()
                            list.addAll(movies.value ?: listOf())
                            list.addAll(it)
                            movies.value = list
                            currentPage += 1
                        } else {
                            lastPageReached = true
                        }
                        viewStatus.value = LoadingStatus.READY
                    }, {
                        Timber.e(it)
                        pageLoadingError.value =
                                getApplication<Application>().applicationContext.getString(R.string.error_loading)
                        viewStatus.value = LoadingStatus.ERROR
                    })
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun reload() {
        movies.value = listOf()
        disposables.add(
            Completable.mergeArray(
                Completable.fromCallable { configurationDao.delete() },
                Completable.fromCallable { genreDao.deleteAll() },
                Completable.fromCallable { movieDao.deleteAll() })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    currentPage = 0
                    lastPageReached = false
                    start()
                })
    }
}