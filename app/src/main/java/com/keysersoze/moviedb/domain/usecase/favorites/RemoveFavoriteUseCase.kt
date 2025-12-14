package com.keysersoze.moviedb.domain.usecase.favorites

import com.keysersoze.moviedb.domain.repository.FavoritesRepository
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(imdbId: String) {
        repository.removeFromFavorites(imdbId)
    }
}