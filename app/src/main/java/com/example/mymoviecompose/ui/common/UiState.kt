package com.example.mymoviecompose.ui.common

import com.example.mymoviecompose.network.response.DetailMovieResponse
import com.example.mymoviecompose.network.response.MovieResponse

sealed interface HomeUiState {
    data class Success(val movie: MovieResponse) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}
sealed interface DetailUiState {
    data class Success(val movie: DetailMovieResponse) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}