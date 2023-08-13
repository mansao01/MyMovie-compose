@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mymoviecompose.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mymoviecompose.navigation.Screen
import com.example.mymoviecompose.ui.component.MyTopAppBar
import com.example.mymoviecompose.ui.screen.detail.DetailScreen
import com.example.mymoviecompose.ui.screen.detail.DetailViewModel
import com.example.mymoviecompose.ui.screen.home.HomeScreen
import com.example.mymoviecompose.ui.screen.home.HomeViewModel

@Composable
fun MovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (currentRoute != Screen.Detail.route){
                MyTopAppBar(scrollBehavior = scrollBehavior)
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
            ) {
                composable(Screen.Home.route) {
                    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
                    HomeScreen(
                        uiState = homeViewModel.uiState,
                        modifier = modifier,
                        navigateToDetail = { movieId ->
                            navController.navigate(Screen.Detail.createRoute(movieId))
                        })
                }

                composable(Screen.Detail.route, arguments = listOf(navArgument("movieId") {
                    type = NavType.IntType
                })) { data ->
                    val movieId = data.arguments?.getInt("movieId") ?: -1
                    val detailViewModel: DetailViewModel =
                        viewModel(factory = DetailViewModel.Factory)
                    DetailScreen(uiState = detailViewModel.uiState, movieId = movieId)
                }
            }
        }
    }


}