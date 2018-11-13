package com.matheusvillela.tmdbupcomingmovies.ui.list

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.matheusvillela.tmdbupcomingmovies.R
import com.matheusvillela.tmdbupcomingmovies.model.domain.Movie
import com.matheusvillela.tmdbupcomingmovies.shared.LoadingStatus
import com.matheusvillela.tmdbupcomingmovies.ui.details.DetailsActivity
import com.matheusvillela.tmdbupcomingmovies.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_list.*


class MoviesListActivity : AppCompatActivity(), MoviesListAdapter.OnLastItemVisibleListener,
    MoviesListAdapter.OnMovieClickedListener {

    private lateinit var viewModel: MoviesListViewModel

    private var adapter: MoviesListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(listActivityToolbar)

        val columnCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                PORTRAIT_COLUMN_COUNT else LANDSCAPE_COLUMN_COUNT
        listActivityRecyclerView.setHasFixedSize(true)
        listActivityRecyclerView.layoutManager = GridLayoutManager(this, columnCount)
        listActivityRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        viewModel = obtainViewModel(MoviesListViewModel::class.java)
        viewModel.viewStatus.observe(this, Observer {
            listActivityLoading.visibility =
                    if (it == LoadingStatus.LOADING) View.VISIBLE else View.GONE
            listActivityError.visibility =
                    if (it == LoadingStatus.ERROR) View.VISIBLE else View.GONE
        })
        viewModel.configuration.observe(this, Observer { config ->
            config?.let {
                adapter = MoviesListAdapter(this, this, it)
                listActivityRecyclerView.adapter = adapter
            }
        })
        viewModel.movies.observe(this, Observer { movies ->
            movies?.let {
                adapter?.setMovies(it)
                listActivitySwipe.visibility = View.VISIBLE
                listActivitySwipe.isRefreshing = false
            }
        })
        viewModel.pageLoadingError.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            listActivitySwipe.isRefreshing = false
        })
        listActivitySwipe.setOnRefreshListener {
            viewModel.reload()
        }
        viewModel.start()
    }

    override fun onMovieClicked(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(DetailsActivity.EXTRAS_MOVIE_ID, movie.id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onLastItemVisible() {
        viewModel.loadNextPage()
    }

    companion object {
        private const val PORTRAIT_COLUMN_COUNT = 1
        private const val LANDSCAPE_COLUMN_COUNT = 2
    }
}
