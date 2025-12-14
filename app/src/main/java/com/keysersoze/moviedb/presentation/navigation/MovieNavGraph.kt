package com.keysersoze.moviedb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.keysersoze.moviedb.presentation.details.MovieDetailsScreen
import com.keysersoze.moviedb.presentation.favorites.FavoritesScreen
import com.keysersoze.moviedb.presentation.search.SearchScreen

@Composable
fun MovieNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route,
        modifier = modifier
    ) {
        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("imdbId") { type = NavType.StringType })
        ) { backStackEntry ->
            val imdbId = backStackEntry.arguments?.getString("imdbId") ?: ""
            MovieDetailsScreen(imdbId = imdbId)
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(navController = navController)
        }
    }
}