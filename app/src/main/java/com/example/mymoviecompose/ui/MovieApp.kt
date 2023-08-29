@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mymoviecompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.example.mymoviecompose.ui.screen.detail.DetailScreen
import com.example.mymoviecompose.ui.screen.detail.DetailViewModel
import com.example.mymoviecompose.ui.screen.favorite.FavoriteScreen
import com.example.mymoviecompose.ui.screen.home.HomeScreen
import com.example.mymoviecompose.ui.screen.home.HomeViewModel
import com.example.mymoviecompose.ui.screen.search.SearchScreen
import com.example.mymoviecompose.ui.screen.search.SearchViewModel
import com.example.mymoviecompose.ui.screen.setting.SettingScreen

@ExperimentalMaterial3Api
@Composable
fun MovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onDarkModeChange: (Boolean) -> Unit

) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            if (currentRoute != Screen.Detail.route && currentRoute != Screen.Setting.route) {
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
                        scrollBehavior = scrollBehavior,
                        navigateToDetail = { movieId ->
                            navController.navigate(Screen.Detail.createRoute(movieId))
                        },
                        navigateToSetting = {
                            navController.navigate(Screen.Setting.route)
                        }
                    )
                }

                composable(Screen.Search.route) {
                    val searchViewModel: SearchViewModel =
                        viewModel(factory = SearchViewModel.Factory)
                    SearchScreen(
                        searchViewModel.uiState,
                        navigateToDetail = { movieId ->
                            navController.navigate(Screen.Detail.createRoute(movieId))
                        },
                        scrollBehavior = scrollBehavior
                    )
                }

                composable(Screen.Setting.route) {
                    SettingScreen(
                        onDarkModeChange = onDarkModeChange,
                        scrollBehavior = scrollBehavior,
                        navigateToHome = { navController.popBackStack() })
                }
                composable(Screen.Favorite.route) {
                    FavoriteScreen(
                        navigateToDetail = { movieId ->
                            navController.navigate(Screen.Detail.createRoute(movieId))
                        }, scrollBehavior = scrollBehavior
                    )
                }

                composable(Screen.Detail.route, arguments = listOf(navArgument("movieId") {
                    type = NavType.IntType
                })) { data ->
                    val movieId = data.arguments?.getInt("movieId") ?: -1
                    val detailViewModel: DetailViewModel =
                        viewModel(factory = DetailViewModel.Factory)
                    DetailScreen(
                        uiState = detailViewModel.uiState,
                        movieId = movieId,
                        navigateToHome = {
                            navController.popBackStack()
                        },
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

        ),
        NavigationItem(
            title = stringResource(R.string.favorite),
            icon = Icons.Default.FavoriteBorder,
            screen = Screen.Favorite,
            contentDescription = stringResource(R.string.favorite)

        )
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp) // Adjust the height as needed
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigationItems.forEach { item ->
            val isSelected = currentRoute == item.screen.route
            val selectedColor = MaterialTheme.colorScheme.onPrimary
            val unselectedColor = MaterialTheme.colorScheme.onBackground.copy(0.6f)



            IconButton(
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .background(
                        if (currentRoute == item.screen.route) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.background
                        },
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription,
                    tint = if (isSelected) selectedColor else unselectedColor
                )
            }
        }
    }
}

