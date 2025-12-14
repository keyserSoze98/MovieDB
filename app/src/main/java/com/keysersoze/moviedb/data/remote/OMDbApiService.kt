package com.keysersoze.moviedb.data.remote

import com.keysersoze.moviedb.BuildConfig
import com.keysersoze.moviedb.data.dto.MovieDetailsDto
import com.keysersoze.moviedb.data.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbApiService {

    @GET(".")
    suspend fun searchMovies(
        @Query("s") title: String,
        @Query("type") type: String = "movie",
        @Query("apikey") apiKey: String = BuildConfig.OMDB_API_KEY
    ): SearchResponseDto

    @GET(".")
    suspend fun getMovieDetails(
        @Query("i") imdbId: String,
        @Query("plot") plot: String = "full",
        @Query("apikey") apiKey: String = BuildConfig.OMDB_API_KEY
    ): MovieDetailsDto
}