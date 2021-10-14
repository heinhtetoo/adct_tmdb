package com.heinhtetoo.tmdb.ui.details

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionSet
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.transition.doOnEnd
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.heinhtetoo.tmdb.R
import com.heinhtetoo.tmdb.data.entities.Movie
import com.heinhtetoo.tmdb.databinding.ActivityDetailsBinding
import com.heinhtetoo.tmdb.ui.BaseActivity
import com.heinhtetoo.tmdb.ui.MovieViewModel
import com.heinhtetoo.tmdb.utils.Constants
import com.heinhtetoo.tmdb.utils.NetworkStateHelper
import com.heinhtetoo.tmdb.utils.loadWithTransistion
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val viewModel by viewModels<MovieViewModel>()

    private var movieId = -1

    companion object {
        private val TAG = DetailsActivity::class.java.simpleName

        const val MOVIE_ID_EXTRA = "MOVIE_ID_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun init() {
        supportPostponeEnterTransition()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.hasExtra(MOVIE_ID_EXTRA)) movieId = intent.getIntExtra(MOVIE_ID_EXTRA, -1)

        binding.ivPoster.transitionName = movieId.toString()
        binding.tvName.transitionName = "${Constants.TITLE_TRANSISTION_HEAD}$movieId"

        bindData()

        observeDetails()
    }

    private fun bindData() {
        binding.layoutNetworkState.tvErrorRetry.setOnClickListener { observeDetails() }
    }

    private fun observeDetails() {
        onLoading()
        viewModel.getMovieById(movieId).observe(this, {
            if (it.id == -1) onError() else bindMovie(it)
        })
    }

    private fun bindMovie(movie: Movie) {
        binding.tvName.text = movie.title
        binding.tvReleaseDate.text = movie.releaseDate
        binding.tvOverview.text = movie.overview

        window.sharedElementEnterTransition = TransitionSet()
            .addTransition(ChangeImageTransform())
            .addTransition(ChangeBounds())
            .apply { doOnEnd { binding.ivPoster.loadWithTransistion(movie.getFullPosterPath()) } }

        binding.ivPoster.loadWithTransistion(movie.getFullPosterPath(), true) {
            supportStartPostponedEnterTransition()
        }

        val resIdFav =
            if (movie.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border

        binding.btnFav.setImageResource(resIdFav)

        binding.btnFav.setOnClickListener { onFavorite(movie.id, !movie.isFavorite) }

        onLoaded()
    }

    private fun onFavorite(id: Int, flag: Boolean) {
        viewModel.updateFavorite(id, flag)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLoading() {
        binding.svContents.isVisible = false
        NetworkStateHelper.onLoading(binding.layoutNetworkState)
    }

    override fun onLoaded() {
        NetworkStateHelper.onLoaded(binding.layoutNetworkState)
        binding.svContents.isVisible = true
    }

    override fun onError() {
        binding.svContents.isVisible = false
        NetworkStateHelper.onError(binding.layoutNetworkState)
    }

}