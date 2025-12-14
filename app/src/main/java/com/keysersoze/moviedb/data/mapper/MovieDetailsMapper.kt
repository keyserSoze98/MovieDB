package com.keysersoze.moviedb.data.mapper

import com.keysersoze.moviedb.data.dto.MovieDetailsDto
import com.keysersoze.moviedb.domain.model.MovieDetails

fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        imdbId = imdbID.orEmpty(),
        title = title.orEmpty(),
        year = year.orEmpty(),
        plot = plot.orEmpty(),
        rating = imdbRating ?: "N/A",
        actors = actors ?: "N/A",
        genre = genre ?: "N/A",
        runtime = runtime ?: "N/A",
        director = director ?: "N/A",
        poster = poster.orEmpty()
    )
}