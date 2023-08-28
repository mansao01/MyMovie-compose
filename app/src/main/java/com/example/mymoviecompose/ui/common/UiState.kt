package com.example.mymoviecompose.ui.common

import com.example.mymoviecompose.data.local.model.Movie
import com.example.mymoviecompose.data.network.response.DetailMovieResponse
import com.example.mymoviecompose.data.network.response.MovieResponse
import com.example.mymoviecompose.data.network.response.SearchMovieResponse
import com.example.mymoviecompose.data.network.response.TrendingMovieResponse

sealed interface HomeUiState {
    data class Success(
        val movie: MovieResponse,
        val trendingMovie: TrendingMovieResponse
    ) : HomeUiState

    object Error : HomeUiState
    object Loading : HomeUiState
}

sealed interface DetailUiState {
    object Default : DetailUiState
    data class Success(val movie: DetailMovieResponse) : DetailUiState
    data class DatabaseTransactionSuccess(val message: String) : DetailUiState
    data class Error(val message:String):DetailUiState
    object Loading : DetailUiState
}

sealed interface SearchUiState {
    data class Success(val movie: SearchMovieResponse) : SearchUiState
    object Error : SearchUiState
    object StandBy : SearchUiState
    object Loading : SearchUiState
}

sealed interface FavoriteUiState{
    data class Result(val movie:List<Movie> = listOf())
}