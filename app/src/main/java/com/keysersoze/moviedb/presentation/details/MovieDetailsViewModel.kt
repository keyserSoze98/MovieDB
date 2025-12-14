package com.keysersoze.moviedb.presentation.details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keysersoze.moviedb.domain.model.Movie
import com.keysersoze.moviedb.domain.model.MovieDetails
import com.keysersoze.moviedb.domain.usecase.GetMovieDetailsUseCase
import com.keysersoze.moviedb.domain.usecase.favorites.AddFavoriteUseCase
import com.keysersoze.moviedb.domain.usecase.favorites.IsFavoriteUseCase
import com.keysersoze.moviedb.domain.usecase.favorites.RemoveFavoriteUseCase
import com.keysersoze.moviedb.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    var uiState by mutableStateOf<UiState<MovieDetails>>(UiState.Loading)
        private set

    var isFavorite by mutableStateOf(false)
        private set

    fun loadMovieDetails(imdbId: String) {
        uiState = UiState.Loading

        viewModelScope.launch {
            isFavorite = isFavoriteUseCase(imdbId)

            getMovieDetailsUseCase(imdbId)
                .onSuccess { details ->
                    uiState = UiState.Success(details)
                }
                .onFailure { error ->
                    uiState = UiState.Error(
                        error.message ?: "Unable to load details"
                    )
                }
        }
    }

    fun toggleFavorite() {
        val movie = (uiState as? UiState.Success)?.data ?: return

        viewModelScope.launch {
            if (isFavorite) {
                removeFavoriteUseCase(movie.imdbId)
                isFavorite = false
            } else {
                addFavoriteUseCase(
                    Movie(
                        imdbId = movie.imdbId,
                        title = movie.title,
                        year = movie.year,
                        poster = movie.poster
                    )
                )
                isFavorite = true
            }
        }
    }
}