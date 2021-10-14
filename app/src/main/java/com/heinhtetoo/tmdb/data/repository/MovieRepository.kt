package com.heinhtetoo.tmdb.data.repository

import com.heinhtetoo.tmdb.data.local.MovieDao
import com.heinhtetoo.tmdb.data.remote.MovieRemoteDataSource
import com.heinhtetoo.tmdb.utils.DataAccessStrategy.performGetOperation
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) {

    fun getUpcoming() = performGetOperation(
        databaseQuery = { localDataSource.getUpcoming() },
        networkCall = { remoteDataSource.getUpcoming() },
        saveCallResult = { localDataSource.insertAll(it.toMovieList(true)) }
    )

    fun getPopular() = performGetOperation(
        databaseQuery = { localDataSource.getPopular() },
        networkCall = { remoteDataSource.getPopular() },
        saveCallResult = { localDataSource.insertAll(it.toMovieList(false)) }
    )

    fun getMovieById(id: Int) = localDataSource.getMovieById(id)

    suspend fun updateFavorite(id: Int, flag: Boolean) = localDataSource.updateFavorite(id, flag)
}