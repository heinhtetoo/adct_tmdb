package com.heinhtetoo.tmdb.data.remote

import com.heinhtetoo.tmdb.utils.Constants
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val movieService: MovieService) :
    BaseDataSource() {

    suspend fun getUpcoming() = getResult { movieService.getUpcoming(Constants.API_KEY) }
    suspend fun getPopular() = getResult { movieService.getPopular(Constants.API_KEY) }

}