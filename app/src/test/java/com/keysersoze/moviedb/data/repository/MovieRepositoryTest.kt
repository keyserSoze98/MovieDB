package com.keysersoze.moviedb.data.repository

import com.keysersoze.moviedb.data.remote.OMDbApiService
import com.keysersoze.moviedb.data.dto.MovieDetailsDto
import com.keysersoze.moviedb.data.dto.SearchMovieDto
import com.keysersoze.moviedb.data.dto.SearchResponseDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryTest {

    private lateinit var api: OMDbApiService
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setup() {
        api = mockk()
        repository = MovieRepositoryImpl(api)
    }

    @Test
    fun `searchMovies returns success when api succeeds`() = runTest {
        val response = SearchResponseDto(
            movies = listOf(
                SearchMovieDto(
                    title = "Batman",
                    year = "2023",
                    imdbID = "tt123",
                    type = "movie",
                    poster = "poster"
                )
            ),
            totalResults = "1",
            response = "True",
            error = null
        )

        coEvery { api.searchMovies("Batman", any()) } returns response

        val result = repository.searchMovies("Batman")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `searchMovies returns failure when api returns error`() = runTest {
        val response = SearchResponseDto(
            movies = null,
            totalResults = null,
            response = "False",
            error = "Movie not found"
        )

        coEvery { api.searchMovies("Random", any()) } returns response

        val result = repository.searchMovies("Random")

        assertTrue(result.isFailure)
    }

    @Test
    fun `getMovieDetails uses cache on second call`() = runTest {
        val dto = MovieDetailsDto(
            title = "Batman",
            year = "2023",
            rated = "PG-13",
            released = "2023",
            runtime = "120 min",
            genre = "Action",
            director = "Director",
            writer = "Writer",
            actors = "Actors",
            plot = "Plot",
            language = "English",
            country = "USA",
            awards = "None",
            poster = "poster",
            imdbRating = "8.5",
            imdbVotes = "1000",
            imdbID = "tt123",
            response = "True",
            error = null
        )

        coEvery { api.getMovieDetails("tt123", any()) } returns dto

        // First call → API hit
        repository.getMovieDetails("tt123")

        // Second call → should hit cache
        repository.getMovieDetails("tt123")

        coVerify(exactly = 1) {
            api.getMovieDetails("tt123", any())
        }
    }
}