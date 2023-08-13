package com.example.mymoviecompose.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.mymoviecompose.network.response.GenresItem

@Composable
fun GenreList(
    genre: List<GenresItem>,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier= modifier) {
        items(genre) { data ->
            ChipView(text = data.name, modifier = Modifier.padding(start = 12.dp))

        }
    }

}