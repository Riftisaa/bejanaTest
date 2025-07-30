package com.takehomechallenge.saputra.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.takehomechallenge.saputra.presentation.detail.DetailScreen
import com.takehomechallenge.saputra.presentation.favorite.FavoriteScreen
import com.takehomechallenge.saputra.presentation.home.HomeScreen
import com.takehomechallenge.saputra.presentation.search.SearchScreen
import com.takehomechallenge.saputra.presentation.splash.SplashScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    onMenuClick: () -> Unit,
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToSearch = { query ->
                    navController.navigate(Screen.Search.createRoute(query))
                },
                onNavigateToDetail = { characterId ->
                    navController.navigate(Screen.Detail.createRoute(characterId))
                },
                onMenuClick = onMenuClick
            )
        }

        composable(route = Screen.Favorite.route) {
            FavoriteScreen(
                onNavigateToDetail = { characterId ->
                    navController.navigate(Screen.Detail.createRoute(characterId))
                }
            )
        }

        composable(
            route = Screen.Search.route,
            arguments = listOf(
                navArgument("query") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: ""
            SearchScreen(
                initialQuery = query,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDetail = { characterId ->
                    navController.navigate(Screen.Detail.createRoute(characterId))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("characterId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
            DetailScreen(
                characterId = characterId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
} 