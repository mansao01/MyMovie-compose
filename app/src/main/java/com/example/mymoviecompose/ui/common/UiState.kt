package com.example.mymoviecompose.ui.common

import com.example.mymoviecompose.network.response.DetailMovieResponse
import com.example.mymoviecompose.network.response.MovieResponse
import com.example.mymoviecompose.network.response.SearchMovieResponse
import com.example.mymoviecompose.network.response.TrendingMovieResponse

sealed interface HomeUiState {
    data class Success(
        val movie: MovieResponse,
        val trendingMovie: TrendingMovieResponse
    ) : HomeUiState

    object Error : HomeUiState
    object Loading : HomeUiState
}

sealed interface DetailUiState {
    data class Success(val movie: DetailMovieResponse) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}

sealed interface SearchUiState {
    data class Success(val movie: SearchMovieResponse) : SearchUiState
    object Error : SearchUiState
    object Loading : SearchUiState
}