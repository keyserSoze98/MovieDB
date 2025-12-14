package com.keysersoze.moviedb.presentation.search

import com.keysersoze.moviedb.domain.model.Movie
import com.keysersoze.moviedb.domain.usecase.SearchMoviesUseCase
import com.keysersoze.moviedb.presentation.state.UiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: SearchViewModel
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        searchMoviesUseCase = mockk()
        viewModel = SearchViewModel(searchMoviesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `empty query returns error state`() {
        viewModel.searchMovies("")

        assertTrue(viewModel.uiState is UiState.Error)
    }

    @Test
    fun `valid query returns success state`() = runTest {
        val movies = listOf(
            Movie("tt123", "Test Movie", "2023", "poster")
        )

        coEvery { searchMoviesUseCase("Batman") } returns Result.success(movies)

        viewModel.searchMovies("Batman")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState is UiState.Success)
    }

    @Test
    fun `api failure returns error state`() = runTest {
        coEvery { searchMoviesUseCase("Batman") } returns
                Result.failure(Exception("Network error"))

        viewModel.searchMovies("Batman")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState is UiState.Error)
    }
}