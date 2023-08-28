package com.example.mymoviecompose.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mymoviecompose.R
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
    if (movieList.isEmpty()) {
        val composition by
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.fav))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(120.dp)
            )
            Text(text = "Nothing here")
        }
    } else {

        LazyColumn {
            items(movieList) { data ->
                MovieFavoriteItemColumn(
                    movie = data,
                    modifier = modifier.clickable { navigateToDetail(data.id) })
            }
        }
    }


}