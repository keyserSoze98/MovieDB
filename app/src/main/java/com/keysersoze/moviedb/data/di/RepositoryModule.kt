package com.keysersoze.moviedb.data.di

import com.keysersoze.moviedb.data.repository.FavoritesRepositoryImpl
import com.keysersoze.moviedb.data.repository.MovieRepositoryImpl
import com.keysersoze.moviedb.domain.repository.FavoritesRepository
import com.keysersoze.moviedb.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(
        impl: FavoritesRepositoryImpl
    ): FavoritesRepository
}