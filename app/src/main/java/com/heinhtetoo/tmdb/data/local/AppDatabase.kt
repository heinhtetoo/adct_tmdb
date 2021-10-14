package com.heinhtetoo.tmdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.heinhtetoo.tmdb.data.entities.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}