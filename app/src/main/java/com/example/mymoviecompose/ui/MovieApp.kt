@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mymoviecompose.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mymoviecompose.R
import com.example.mymoviecompose.navigation.NavigationItem
import com.example.mymoviecompose.navigation.Screen
import com.example.mymoviecompose.ui.component.MyTopAppBar
import com.example.mymoviecompose.ui.screen.detail.DetailScreen
import com.example.mymoviecompose.ui.screen.detail.DetailViewModel
import com.example.mymoviecompose.ui.screen.home.HomeScreen
import com.example.mymoviecompose.ui.screen.home.HomeViewModel
import com.example.mymoviecompose.ui.screen.search.SearchScreen

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
            if (currentRoute != Screen.Detail.route) {
                MyTopAppBar(scrollBehavior = scrollBehavior)
            }
        },
        bottomBar = {
            if (currentRoute != Screen.Detail.route){
                BottomBar(navController = navController)
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

                composable(Screen.Search.route) {
                    SearchScreen()
                }

                composable(Screen.Detail.route, arguments = listOf(navArgument("movieId") {
                    type = NavType.IntType
                })) { data ->
                    val movieId = data.arguments?.getInt("movieId") ?: -1
                    val detailViewModel: DetailViewModel =
                        viewModel(factory = DetailViewModel.Factory)
                    DetailScreen(
                        uiState = detailViewModel.uiState,
                        movieId = movieId, navigateToHome = {
                            navController.navigate(Screen.Home.route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.home),
                icon = Icons.Default.Home,
                screen = Screen.Home,
                contentDescription = stringResource(R.string.home)

            ),
            NavigationItem(
                title = stringResource(R.string.search),
                icon = Icons.Default.Search,
                screen = Screen.Search,
                contentDescription = stringResource(R.string.search)

            )
        )
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.background
        ) {
            navigationItems.map { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.screen.route,
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.contentDescription,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    label = {
                        Text(text = item.title)
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.primary.copy(0.4f),
                    selectedContentColor = MaterialTheme.colorScheme.tertiaryContainer,
                )
            }
        }
    }

}