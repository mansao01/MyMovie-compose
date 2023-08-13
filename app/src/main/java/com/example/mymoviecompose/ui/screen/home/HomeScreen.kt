package com.example.mymoviecompose.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

        is HomeUiState.Error -> ErrorScreen()
    }
}

@Composable
fun HomeContent(
    movie: List<ResultsItem>,
    movieTrending: List<ResultsItemTrending>,
    navigateToDetail: (Int) -> Unit,
    modifier:Modifier = Modifier
) {
    Column(modifier = modifier) {
        HomeSection(title = "Popular Today") {
            MovieListRowItem(
                movie = movieTrending,
                navigateToDetail = navigateToDetail
            )
        }
        HomeSection(title = "Discover  Movie") {
            MovieListColumnItem(
                movie = movie,
                navigateToDetail = navigateToDetail
            )
        }
    }
}

@Composable
fun MovieListColumnItem(
    movie: List<ResultsItem>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(movie) { data ->
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
    LazyRow {
        items(movie) { data ->
            MovieItemRow(movie = data,
                modifier = modifier
                    .clickable { navigateToDetail(data.id) })
        }
    }
}