package com.takehomechallenge.saputra.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object Search : Screen("search?query={query}") {
        fun createRoute(query: String = "") = if (query.isEmpty()) "search" else "search?query=$query"
    }
    data object Detail : Screen("detail/{characterId}") {
        fun createRoute(characterId: Int) = "detail/$characterId"
    }
    data object Favorite : Screen("favorite")
} 