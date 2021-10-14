package com.heinhtetoo.tmdb.data.response

import com.google.gson.annotations.SerializedName
import com.heinhtetoo.tmdb.data.entities.Movie

data class MovieListResponse(
    @SerializedName("results")
    val results: List<MovieResponse>
) {
    fun toMovieList(isUpcoming: Boolean): List<Movie> {
        return results.map {
            Movie(
                it.id,
                it.overview,
                it.posterPath,
                it.releaseDate,
                it.title,
                isUpcoming,
                false
            )
        }
    }
}