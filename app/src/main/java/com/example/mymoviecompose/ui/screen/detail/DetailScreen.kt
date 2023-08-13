package com.example.mymoviecompose.ui.screen.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymoviecompose.R
import com.example.mymoviecompose.network.response.DetailMovieResponse
import com.example.mymoviecompose.ui.common.DetailUiState
import com.example.mymoviecompose.ui.component.ErrorScreen
import com.example.mymoviecompose.ui.component.GenreList
import com.example.mymoviecompose.ui.component.LoadingScreen

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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(330.dp)
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(size = 4.dp))

        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data("https://image.tmdb.org/t/p/original/${movie.backdropPath}")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    modifier = Modifier
                        .background(color = Color.Gray, shape = RectangleShape)
                        .fillMaxWidth()
                )
                Text(
                    text = movie.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 26.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 16.dp)
                )
                GenreList(
                    genre = movie.genres,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                )
            }

        }
        Text(
            text = movie.overview, modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp)
        )
    }


    Log.d("DetailScreen", movie.posterPath)
}


