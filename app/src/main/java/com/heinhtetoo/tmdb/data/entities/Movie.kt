package com.heinhtetoo.tmdb.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.heinhtetoo.tmdb.utils.Constants

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val isUpcoming: Boolean,
    var isFavorite: Boolean
) {
    fun getTitleTransactionName(): String = "${Constants.TITLE_TRANSISTION_HEAD}$id"
    fun getPosterTransactionName(): String = id.toString()
    fun getFullPosterPath(): String {
        return "${Constants.POSTER_PATH}$posterPath"
    }
}