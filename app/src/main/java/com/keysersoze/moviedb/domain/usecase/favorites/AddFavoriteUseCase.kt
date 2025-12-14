package com.keysersoze.moviedb.domain.usecase.favorites

import com.keysersoze.moviedb.domain.model.Movie
import com.keysersoze.moviedb.domain.repository.FavoritesRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(movie: Movie) {
        repository.addToFavorites(movie)
    }
}