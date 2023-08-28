package com.example.mymoviecompose.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mymoviecompose.data.local.model.Movie
import com.example.mymoviecompose.ui.component.MovieFavoriteItemColumn

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    detailVieModel: FavoriteVieModel = viewModel(factory = FavoriteVieModel.Factory)
) {
    val detailUiState by detailVieModel.favUiState.collectAsState()

    MovieFavList(
        movieList = detailUiState.movie,
        navigateToDetail = navigateToDetail,
        modifier = modifier
    )
}

@Composable
fun MovieFavList(
    movieList: List<Movie>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit
) {

    LazyColumn {
        items(movieList) { data ->
            MovieFavoriteItemColumn(
                movie = data,
                modifier = modifier.clickable { navigateToDetail(data.id) })
        }
    }

}