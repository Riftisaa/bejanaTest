package com.takehomechallenge.saputra.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.takehomechallenge.saputra.presentation.common.NavigationDrawer
import com.takehomechallenge.saputra.presentation.navigation.MainNavGraph
import com.takehomechallenge.saputra.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawer(
                onSearchClick = {
                    coroutineScope.launch {
                        drawerState.close()
                        navController.navigate(Screen.Search.createRoute(""))
                    }
                },
                onFavoriteClick = {
                    coroutineScope.launch {
                        drawerState.close()
                        navController.navigate(Screen.Favorite.route)
                    }
                }
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                MainNavGraph(
                    navController = navController,
                    onMenuClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }
                )
                
                // Overlay untuk menutup drawer ketika tap di luar area drawer
                if (drawerState.isOpen) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clickable {
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                            }
                    )
                }
            }
        }
    )
}
