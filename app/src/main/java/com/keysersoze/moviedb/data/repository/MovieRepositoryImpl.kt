package com.keysersoze.moviedb.data.repository

import com.keysersoze.moviedb.data.api.OMDbApiService
import com.keysersoze.moviedb.data.mapper.toDomain
import com.keysersoze.moviedb.domain.model.Movie
import com.keysersoze.moviedb.domain.model.MovieDetails
import com.keysersoze.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: OMDbApiService
) : MovieRepository {

    private val movieDetailsCache = mutableMapOf<String, MovieDetails>()

    override suspend fun searchMovies(query: String): Result<List<Movie>> {
        return try {
            if (query.isBlank()) {
                return Result.success(emptyList())
            }

            val response = api.searchMovies(query)

            if (response.response == "True") {
                val movies = response.movies
                    ?.map { it.toDomain() }
                    ?: emptyList()

                Result.success(movies)
            } else {
                Result.failure(
                    Exception(response.error ?: "No movies found")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieDetails(imdbId: String): Result<MovieDetails> {
        movieDetailsCache[imdbId]?.let {
            return Result.success(it)
        }

        return try {
            val response = api.getMovieDetails(imdbId)

            if (response.response == "True") {
                val details = response.toDomain()
                movieDetailsCache[imdbId] = details
                Result.success(details)
            } else {
                Result.failure(
                    Exception(response.error ?: "Unable to load movie details")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}