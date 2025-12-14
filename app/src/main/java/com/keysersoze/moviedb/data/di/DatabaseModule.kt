package com.keysersoze.moviedb.data.di

import android.content.Context
import androidx.room.Room
import com.keysersoze.moviedb.data.room.FavoriteMovieDao
import com.keysersoze.moviedb.data.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "moviedb.db"
        ).build()
    }

    @Provides
    fun provideFavoriteMovieDao(
        database: MovieDatabase
    ): FavoriteMovieDao = database.favoriteMovieDao()
}