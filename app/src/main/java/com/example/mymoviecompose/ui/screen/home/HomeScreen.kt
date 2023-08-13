package com.example.mymoviecompose.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.example.mymoviecompose.network.response.ResultsItem
import com.example.mymoviecompose.network.response.ResultsItemTrending
import com.example.mymoviecompose.ui.common.HomeUiState
import com.example.mymoviecompose.ui.component.ErrorScreen
import com.example.mymoviecompose.ui.component.HomeSection
import com.example.mymoviecompose.ui.component.LoadingScreen
import com.example.mymoviecompose.ui.component.MovieItemColumn
import com.example.mymoviecompose.ui.component.MovieItemRow

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    navigateToDetail: (Int) -> Unit
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

@Composable
fun HomeContent(
    movie: List<ResultsItem>,
    movieTrending: List<ResultsItemTrending>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HomeSection(title = "Popular Today") {
            MovieListRowItem(
                movie = movieTrending,
                navigateToDetail = navigateToDetail
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        HomeSection(title = "Discover  Movie") {
            MovieListColumnItem(
                movie = movie,
                navigateToDetail = navigateToDetail
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "YOOOOOO")
    }
}

@Composable
fun MovieListColumnItem(
    movie: List<ResultsItem>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = Modifier.height(300.dp)) {
        items(movie, key = { it.id }) { data ->
            MovieItemColumn(
                movie = data,
                modifier = modifier
                    .clickable { navigateToDetail(data.id) })
        }
    }
}

@Composable
fun MovieListRowItem(
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