package com.example.mymoviecompose.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.example.mymoviecompose.network.response.ResultsItemSearch
import com.example.mymoviecompose.ui.common.SearchUiState
import com.example.mymoviecompose.ui.component.ErrorScreen
import com.example.mymoviecompose.ui.component.LoadingScreen
import com.example.mymoviecompose.ui.component.MySearchBar
import com.example.mymoviecompose.ui.component.NotFoundScreen
import com.example.mymoviecompose.ui.component.SearchMovieItemColumn
import com.example.mymoviecompose.ui.component.StandByScreen

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory),
    navigateToDetail: (Int) -> Unit

) {
    Column {
        MySearchBar(
            searchViewModel = searchViewModel,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        when (uiState) {
            is SearchUiState.StandBy -> StandByScreen(message = "Please search movie to show result")
            is SearchUiState.Loading -> LoadingScreen()
            is SearchUiState.Success -> SearchMovieListItem(
                movie = uiState.movie.results,
                navigateToDetail = navigateToDetail
            )

            is SearchUiState.Error -> ErrorScreen()
        }
    }
}

@Composable
fun SearchMovieListItem(
    movie: List<ResultsItemSearch>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit
) {
    if (movie.isNotEmpty()) {

        LazyColumn(modifier = modifier) {
            items(movie) { data ->
                SearchMovieItemColumn(
                    movie = data,
                    modifier.clickable { navigateToDetail(data.id) })
            }

        }
    } else {
        NotFoundScreen(message = "Movie Not Found")
    }
}