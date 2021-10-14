package com.heinhtetoo.tmdb.di

import android.content.Context
import androidx.room.Room
import com.heinhtetoo.tmdb.data.local.AppDatabase
import com.heinhtetoo.tmdb.data.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "tmdb.db")
            .fallbackToDestructiveMigration().build()
    }
}