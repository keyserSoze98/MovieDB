package com.keysersoze.moviedb.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keysersoze.moviedb.domain.model.Movie
import com.keysersoze.moviedb.domain.usecase.SearchMoviesUseCase
import com.keysersoze.moviedb.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    var uiState by mutableStateOf<UiState<List<Movie>>>(UiState.Idle)
        private set

    fun searchMovies(query: String) {
        if (query.isBlank()) {
            uiState = UiState.Error("Please enter a movie name")
            return
        }

        uiState = UiState.Loading

        viewModelScope.launch {
            searchMoviesUseCase(query)
                .onSuccess { movies ->
                    uiState = UiState.Success(movies)
                }
                .onFailure { error ->
                    uiState = UiState.Error(error.message ?: "Something went wrong")
                }
        }
    }
}