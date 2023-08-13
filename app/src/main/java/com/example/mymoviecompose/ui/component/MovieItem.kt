package com.example.mymoviecompose.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymoviecompose.R
import com.example.mymoviecompose.network.response.MovieResponse
import com.example.mymoviecompose.network.response.ResultsItem

@Composable
fun MovieItemColumn(
    movie: ResultsItem, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("https://image.tmdb.org/t/p/original/${movie.posterPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                modifier = Modifier
                    .size(68.dp)
                    .align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 4.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = movie.overview,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun MovieItemRow2(movie: ResultsItem, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .size(width = 320.dp, height = 400.dp)
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
                modifier = Modifier
                    .background(color = Color.Gray, shape = RectangleShape)
                    .size(width = 320.dp, height = 180.dp)
                    .fillMaxWidth(1.0f)
            )
            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 0.dp)
            )

            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }
    }
}

@Composable
fun MovieItemRow(movie: ResultsItem, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .size(width = 120.dp, height = 120.dp)
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
                modifier = Modifier
                    .background(color = Color.Gray, shape = RectangleShape)
                    .size(width = 120.dp, height = 40.dp)
                    .fillMaxWidth(1.0f)
            )
            Text(
                text = movie.title,
//                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 0.dp)
            )


        }
    }
}