package com.example.mymoviecompose.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mymoviecompose.data.local.model.Movie
import com.example.mymoviecompose.data.network.response.DetailMovieResponse
import com.example.mymoviecompose.data.network.response.MovieResponse
import com.example.mymoviecompose.data.network.response.SearchMovieResponse
import com.example.mymoviecompose.data.network.response.TrendingMovieResponse
import kotlinx.coroutines.flow.Flow

sealed interface HomeUiState {
    data class Success(
        val movie: MovieResponse,
        val trendingMovie: TrendingMovieResponse
    ) : HomeUiState

    object Error : HomeUiState
    object Loading : HomeUiState
}

sealed interface DetailUiState {
    data class Success(val movie: DetailMovieResponse,val movieFlow: Flow<Movie>) : DetailUiState
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

sealed interface SettingUiState {
    data class SettingUiState(
        val isDarkMode: Boolean = false,
        val title: String = if (isDarkMode) "Dark Mode" else "Light Mode",
        val icon: ImageVector =
            if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode
    )

}