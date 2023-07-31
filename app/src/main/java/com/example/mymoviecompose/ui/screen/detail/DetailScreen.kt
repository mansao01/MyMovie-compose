package com.example.mymoviecompose.ui.screen.detail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymoviecompose.R
import com.example.mymoviecompose.network.response.DetailMovieResponse
import com.example.mymoviecompose.network.response.GenresItem
import com.example.mymoviecompose.ui.common.DetailUiState
import com.example.mymoviecompose.ui.common.HomeUiState
import com.example.mymoviecompose.ui.component.ErrorScreen
import com.example.mymoviecompose.ui.component.LoadingScreen
import com.example.mymoviecompose.ui.screen.home.MovieListItem

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    movieId: Int,
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = viewModel(factory = DetailViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        detailViewModel.getDetailMovie(movieId)
    }
    when (uiState) {
        is DetailUiState.Loading -> LoadingScreen()
        is DetailUiState.Success -> DetailContent(movie = uiState.movie)
        is DetailUiState.Error -> ErrorScreen()
    }
}

@Composable
fun DetailContent(
    movie: DetailMovieResponse,
    modifier: Modifier = Modifier
) {
    Column {
        Column(
            modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("https://image.tmdb.org/t/p/original/${movie.posterPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(220.dp)
            )
            Text(
                text = movie.title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp)
            )
            GenreList(genre = movie.genres)
        }
        Text(text = movie.overview, modifier = Modifier.padding(top = 12.dp))
    }
    Log.d("DetailScreen", movie.posterPath)
}

@Composable
fun GenreList(
    genre: List<GenresItem>,
    modifier: Modifier = Modifier
) {
    LazyRow() {
        items(genre) { data ->
            Text(
                text = "${data.name} ",
                fontStyle = FontStyle.Italic,
                modifier = Modifier

            )
        }
    }

}