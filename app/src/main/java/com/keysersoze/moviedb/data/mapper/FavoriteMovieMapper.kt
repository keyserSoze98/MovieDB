package com.keysersoze.moviedb.data.mapper

import com.keysersoze.moviedb.data.room.FavoriteMovieEntity
import com.keysersoze.moviedb.domain.model.Movie

fun FavoriteMovieEntity.toDomain(): Movie {
    return Movie(
        imdbId = imdbId,
        title = title,
        year = year,
        poster = poster
    )
}

fun Movie.toEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        imdbId = imdbId,
        title = title,
        year = year,
        poster = poster
    )
}