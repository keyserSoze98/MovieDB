package com.keysersoze.moviedb.domain.repository

import com.keysersoze.moviedb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    suspend fun addToFavorites(movie: Movie)

    suspend fun removeFromFavorites(imdbId: String)

    fun getFavorites(): Flow<List<Movie>>

    suspend fun isFavorite(imdbId: String): Boolean
}