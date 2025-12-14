package com.keysersoze.moviedb.data.dto

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    @SerializedName("Search") val movies: List<SearchMovieDto>?,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Response") val response: String?,
    @SerializedName("Error") val error: String?
)