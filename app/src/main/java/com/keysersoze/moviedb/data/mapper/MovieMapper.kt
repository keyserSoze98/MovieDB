package com.keysersoze.moviedb.data.mapper

import com.keysersoze.moviedb.data.dto.SearchMovieDto
import com.keysersoze.moviedb.domain.model.Movie

fun SearchMovieDto.toDomain(): Movie {
    return Movie(
        imdbId = imdbID.orEmpty(),
        title = title.orEmpty(),
        year = year.orEmpty(),
        poster = poster.orEmpty()
    )
}