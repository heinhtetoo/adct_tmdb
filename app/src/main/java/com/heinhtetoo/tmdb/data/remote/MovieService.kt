package com.heinhtetoo.tmdb.data.remote

import com.heinhtetoo.tmdb.data.response.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("api_key") apiKey: String) : Response<MovieListResponse>

    @GET("movie/popular")
    suspend fun getPopular(@Query("api_key") apiKey: String): Response<MovieListResponse>
}