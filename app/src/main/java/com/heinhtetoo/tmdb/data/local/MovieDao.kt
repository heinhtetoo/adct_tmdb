package com.heinhtetoo.tmdb.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.heinhtetoo.tmdb.data.entities.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE isUpcoming = 1")
    fun getUpcoming(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE isUpcoming = 0")
    fun getPopular(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE id=:id")
    fun getMovieById(id: Int): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("UPDATE movies SET isFavorite=:isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("DELETE FROM movies WHERE isUpcoming = 1")
    suspend fun deleteUpcoming()

    @Query("DELETE FROM movies WHERE isUpcoming = 0")
    suspend fun deletePopular()

    @Query("DELETE FROM movies")
    suspend fun nukeTable()
}