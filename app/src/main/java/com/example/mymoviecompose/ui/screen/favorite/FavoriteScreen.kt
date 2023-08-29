@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mymoviecompose.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    detailVieModel: FavoriteVieModel = viewModel(factory = FavoriteVieModel.Factory)
) {
    val detailUiState by detailVieModel.favUiState.collectAsState()
    Scaffold(
        topBar = { FavScreenTopBar(scrollBehavior = scrollBehavior) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            MovieFavList(
                movieList = detailUiState.movie,
                navigateToDetail = navigateToDetail,
                modifier = modifier
            )
        }
    }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.favorite),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = modifier
    )

}