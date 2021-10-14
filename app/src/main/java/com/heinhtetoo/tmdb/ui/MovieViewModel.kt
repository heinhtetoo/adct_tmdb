package com.heinhtetoo.tmdb.ui

import androidx.lifecycle.*
import com.heinhtetoo.tmdb.data.entities.Movie
import com.heinhtetoo.tmdb.data.repository.MovieRepository
import com.heinhtetoo.tmdb.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val reloadTrigger = MutableLiveData<Boolean>()

    init {
        refreshMovies()
    }

    fun refreshMovies() {
        reloadTrigger.value = true
    }

    private val _upcoming: LiveData<Resource<List<Movie>>> =
        Transformations.switchMap(reloadTrigger) { repository.getUpcoming() }
    val upcoming get() = _upcoming

    private val _popular: LiveData<Resource<List<Movie>>> =
        Transformations.switchMap(reloadTrigger) { repository.getPopular() }
    val popular get() = _popular

    fun getMovieById(id: Int) = repository.getMovieById(id)

    fun updateFavorite(id: Int, flag: Boolean) =
        viewModelScope.launch { repository.updateFavorite(id, flag) }
}