package com.keysersoze.moviedb.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.keysersoze.moviedb.presentation.state.UiState

@Composable
fun MovieDetailsScreen(
    imdbId: String,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(imdbId) {
        viewModel.loadMovieDetails(imdbId)
    }

    when (val state = viewModel.uiState) {

        UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            val movie = state.data

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                item {
                    AsyncImage(
                        model = movie.poster,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(420.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                item {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "${movie.title} (${movie.year})",
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.weight(1f)
                            )

                            IconButton(
                                onClick = { viewModel.toggleFavorite() }
                            ) {
                                Icon(
                                    imageVector = if (viewModel.isFavorite)
                                        Icons.Filled.Favorite
                                    else
                                        Icons.Outlined.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "⭐ ${movie.rating}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = movie.plot)

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "🎭 Cast: ${movie.actors}")

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "🎬 Director: ${movie.director}")

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "⏱ Runtime: ${movie.runtime}")

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "🏷 Genre: ${movie.genre}")
                    }
                }
            }
        }

        is UiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        else -> Unit
    }
}