package com.keysersoze.moviedb.domain.usecase

import com.keysersoze.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(query: String) =
        repository.searchMovies(query.trim())
}