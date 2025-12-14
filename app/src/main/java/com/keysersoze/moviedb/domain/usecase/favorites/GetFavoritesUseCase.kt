package com.keysersoze.moviedb.domain.usecase.favorites

import com.keysersoze.moviedb.domain.repository.FavoritesRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke() = repository.getFavorites()
}