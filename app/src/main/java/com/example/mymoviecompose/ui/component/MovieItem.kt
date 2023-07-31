package com.example.mymoviecompose.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymoviecompose.R
import com.example.mymoviecompose.network.response.MovieResponse
import com.example.mymoviecompose.network.response.ResultsItem

@Composable
fun MovieItem(
    movie: ResultsItem, modifier: Modifier = Modifier
) {
    Card(modifier = modifier.padding(8.dp), shape = MaterialTheme.shapes.medium) {
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
                    .size(60.dp)
            )
            Column(modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 6.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}