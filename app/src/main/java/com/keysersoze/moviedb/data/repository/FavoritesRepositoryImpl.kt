package com.keysersoze.moviedb.data.repository

import com.keysersoze.moviedb.data.mapper.toDomain
import com.keysersoze.moviedb.data.mapper.toEntity
import com.keysersoze.moviedb.data.room.FavoriteMovieDao
import com.keysersoze.moviedb.domain.model.Movie
import com.keysersoze.moviedb.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoriteMovieDao
) : FavoritesRepository {

    override suspend fun addToFavorites(movie: Movie) {
        dao.insert(movie.toEntity())
    }

    override suspend fun removeFromFavorites(imdbId: String) {
        dao.delete(imdbId)
    }

    override fun getFavorites(): Flow<List<Movie>> {
        return dao.getAllFavorites()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun isFavorite(imdbId: String): Boolean {
        return dao.isFavorite(imdbId)
    }
}