package com.example.mymoviecompose.ui.screen.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymoviecompose.R
import com.example.mymoviecompose.data.local.model.Movie
import com.example.mymoviecompose.data.network.response.DetailMovieResponse
import com.example.mymoviecompose.ui.common.DetailUiState
import com.example.mymoviecompose.ui.component.ErrorScreen
import com.example.mymoviecompose.ui.component.GenreList
import com.example.mymoviecompose.ui.component.LoadingScreen

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    movieId: Int,
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = viewModel(factory = DetailViewModel.Factory),
    navigateToHome: () -> Unit,

) {
    LaunchedEffect(Unit) {
        detailViewModel.getDetailMovie(movieId)
    }
    when (uiState) {
        is DetailUiState.Loading -> LoadingScreen()
        is DetailUiState.Success -> DetailContent(
            movie = uiState.movie,
            navigateToHome = navigateToHome,
            modifier = modifier,
            detailViewModel = detailViewModel,
        )

        is DetailUiState.Error -> ErrorScreen()
    }
}

@Composable
fun DetailContent(
    movie: DetailMovieResponse,
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    detailViewModel: DetailViewModel
) {
    val context = LocalContext.current
    val isFavorite = detailViewModel.isFavorite.value

    val movieEntity = Movie(
        id = movie.id,
        title = movie.title,
        photoUrl = "https://image.tmdb.org/t/p/original/${movie.backdropPath}", // Adjust this URL as needed
        isFavorite = isFavorite
    )

    Column(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(230.dp)
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(size = 4.dp))
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
            ) {
                Box {
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
                            .heightIn(230.dp)
                    )
                    IconButton(
                        onClick = { navigateToHome() },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = stringResource(R.string.back),
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.background,
                                    shape = CircleShape
                                )
                                .padding(8.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            if (isFavorite) {
                                detailViewModel.deleteMovieFromFavor(movieEntity)
                            } else {
                                detailViewModel.insertMovieToFavor(movieEntity)
                                Toast.makeText(context, detailViewModel.message.value, Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = stringResource(R.string.favorite),
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.background,
                                    shape = CircleShape
                                )
                                .padding(8.dp)
                        )
                    }
                }

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
            text = movie.overview,
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun MToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

