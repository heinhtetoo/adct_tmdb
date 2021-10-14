package com.heinhtetoo.tmdb.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.heinhtetoo.tmdb.databinding.ActivityMainBinding
import com.heinhtetoo.tmdb.ui.BaseActivity
import com.heinhtetoo.tmdb.ui.MovieAdapter
import com.heinhtetoo.tmdb.ui.MovieViewModel
import com.heinhtetoo.tmdb.ui.details.DetailsActivity
import com.heinhtetoo.tmdb.utils.Constants
import com.heinhtetoo.tmdb.utils.NetworkStateHelper
import com.heinhtetoo.tmdb.utils.Resource.Status.*
import androidx.core.util.Pair
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), MovieAdapter.MovieItemListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MovieViewModel>()
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var popularAdapter: MovieAdapter

    private var dataLoadState = MutableLiveData(Pair(first = false, second = false))

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setupAdapters()

        observeData()

        observeUpcoming()

        observePopular()

        binding.layoutSwipeRefresh.setOnRefreshListener {
            binding.layoutSwipeRefresh.isRefreshing = true
            viewModel.refreshMovies()
        }
    }

    private fun setupAdapters() {
        upcomingAdapter = MovieAdapter(this)
        binding.rvUpcoming.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingAdapter
        }

        popularAdapter = MovieAdapter(this)
        binding.rvPopular.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }

    private fun observeData() {
        dataLoadState.observe(this, {
            if (it.first && it.second) onLoaded() else onLoading()
        })
    }

    private fun observeUpcoming() {
        viewModel.upcoming.observe(this, {
            when (it.status) {
                SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) upcomingAdapter.setItems(ArrayList(it.data))
                }
                ERROR -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                LOADING -> {
                }
            }

            dataLoadState.value =
                Pair(first = it.status != LOADING, second = dataLoadState.value?.second ?: false)
        })
    }

    private fun observePopular() {
        viewModel.popular.observe(this, {
            when (it.status) {
                SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) popularAdapter.setItems(ArrayList(it.data))
                }
                ERROR -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                LOADING -> {
                }
            }

            dataLoadState.value =
                Pair(first = dataLoadState.value?.first ?: false, second = it.status != LOADING)
        })
    }

    override fun onItemClick(id: Int, posterView: View, titleView: View) {
        goToMovieDetails(id, posterView, titleView)
    }

    override fun onFavoriteClick(id: Int, flag: Boolean) {
        viewModel.updateFavorite(id, flag)
    }

    override fun onLoading() {
        binding.svContents.isVisible = false
        NetworkStateHelper.onLoading(binding.layoutNetworkState)
    }

    override fun onLoaded() {
        NetworkStateHelper.onLoaded(binding.layoutNetworkState)
        binding.svContents.isVisible = true
        binding.layoutSwipeRefresh.isRefreshing = false
    }

    override fun onError() {}

    private fun goToMovieDetails(id: Int, posterView: View, titleView: View) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.MOVIE_ID_EXTRA, id)
        }

        val posterPair = Pair(posterView, id.toString())
        val titlePair = Pair(titleView, "${Constants.TITLE_TRANSISTION_HEAD}$id")

        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, posterPair, titlePair)
        startActivity(intent, options.toBundle())
    }

}