package com.keysersoze.moviedb.domain.usecase.favorites

import com.keysersoze.moviedb.domain.repository.FavoritesRepository
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(imdbId: String): Boolean {
        return repository.isFavorite(imdbId)
    }
}