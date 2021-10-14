package com.heinhtetoo.tmdb.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.heinhtetoo.tmdb.data.local.MovieDao
import com.heinhtetoo.tmdb.data.remote.MovieRemoteDataSource
import com.heinhtetoo.tmdb.data.remote.MovieService
import com.heinhtetoo.tmdb.data.repository.MovieRepository
import com.heinhtetoo.tmdb.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(movieService: MovieService) =
        MovieRemoteDataSource(movieService)

    @Singleton
    @Provides
    fun provideMovieRepository(
        remoteDataSource: MovieRemoteDataSource,
        localDataSource: MovieDao
    ) = MovieRepository(remoteDataSource, localDataSource)
}