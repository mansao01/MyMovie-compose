package com.example.mymoviecompose.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mymoviecompose.R
import com.example.mymoviecompose.data.network.response.ResultsItem
import com.example.mymoviecompose.data.network.response.ResultsItemTrending
import com.example.mymoviecompose.ui.common.HomeUiState
import com.example.mymoviecompose.ui.component.ErrorScreen
import com.example.mymoviecompose.ui.component.HomeSection
import com.example.mymoviecompose.ui.component.LoadingScreen
import com.example.mymoviecompose.ui.component.MovieItemColumn
import com.example.mymoviecompose.ui.component.MovieItemRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    navigateToDetail: (Int) -> Unit,
    navigateToSetting: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    Scaffold(
        topBar = {
            HomeScreenTopAppBar(
                scrollBehavior = scrollBehavior,
                navigateToSetting = navigateToSetting
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (uiState) {
                is HomeUiState.Loading -> LoadingScreen()
                is HomeUiState.Success -> HomeContent(
                    movie = uiState.movie.results,
                    movieTrending = uiState.trendingMovie.resultsTrending,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier
                )

                is HomeUiState.Error -> ErrorScreen(
                    modifier = Modifier.clickable {
                        homeViewModel.getMovies()
                    })
            }
        }
    }
}

@Composable
fun HomeContent(
    movie: List<ResultsItem>,
    movieTrending: List<ResultsItemTrending>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HomeSection(
            title = stringResource(R.string.trending_today),
            modifier = Modifier.clickable {
                Toast.makeText(
                    context,
                    "Under developing",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            MovieListRowPopularToday(
                movie = movieTrending,
                navigateToDetail = navigateToDetail
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        HomeSection(title = stringResource(R.string.discover_movie)) {
            MovieListColumnDiscover(
                movie = movie,
                navigateToDetail = navigateToDetail
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MovieListColumnDiscover(
    movie: List<ResultsItem>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
//    LazyColumn(modifier = Modifier.height(300.dp)) {
//        items(movie, key = { it.id }) { data ->
//            MovieItemColumn(
//                movie = data,
//                modifier = modifier
//                    .clickable { navigateToDetail(data.id) })
//        }
//    }
    LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        modifier = Modifier.height(300.dp)
    ) {
        items(movie, key = { it.id }) { data ->
            MovieItemColumn(movie = data,
                modifier = modifier
                    .clickable { navigateToDetail(data.id) }
            )
        }
    }
}

@Composable
fun MovieListRowPopularToday(
    movie: List<ResultsItemTrending>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
    ) {
        items(movie, key = { it.id }) { data ->
            MovieItemRow(movie = data,
                modifier = modifier
                    .clickable { navigateToDetail(data.id) }
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun HomeScreenTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    navigateToSetting: () -> Unit
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.movie_app),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        actions = {
            IconButton(onClick = { navigateToSetting() }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            }
        },
        modifier = modifier
    )
}