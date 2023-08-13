package com.example.mymoviecompose.ui.screen.search

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text

@Composable
fun SearchScreen(searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)) {
    Text(text = searchViewModel.greeting)
}