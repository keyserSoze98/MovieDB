package com.keysersoze.moviedb.domain.repository

import com.keysersoze.moviedb.domain.model.Movie
import com.keysersoze.moviedb.domain.model.MovieDetails

interface MovieRepository {

    suspend fun searchMovies(query: String): Result<List<Movie>>

    suspend fun getMovieDetails(imdbId: String): Result<MovieDetails>
}