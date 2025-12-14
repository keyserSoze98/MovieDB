package com.keysersoze.moviedb.presentation.navigation

sealed class Screen(val route: String) {
    object Search : Screen("search")
    object Details : Screen("details/{imdbId}") {
        fun createRoute(imdbId: String) = "details/$imdbId"
    }
    object Favorites : Screen("favorites")
}