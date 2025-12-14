package com.keysersoze.moviedb.domain.usecase

import com.keysersoze.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(imdbId: String) = repository.getMovieDetails(imdbId)
}