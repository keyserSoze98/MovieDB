package com.keysersoze.moviedb.domain.model

data class MovieDetails(
    val imdbId: String,
    val title: String,
    val year: String,
    val plot: String,
    val rating: String,
    val actors: String,
    val genre: String,
    val runtime: String,
    val director: String,
    val poster: String
)