package com.example.mymoviecompose.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mymoviecompose.MovieApplication
import com.example.mymoviecompose.data.MovieRepository
import com.example.mymoviecompose.ui.common.HomeUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    var uiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            uiState = HomeUiState.Loading
            uiState = try {
                val result = repository.getMovies()
                val resultTrendingMovies = repository.getTrendingMovie()
                HomeUiState.Success(result, resultTrendingMovies)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
                val movieRepository = application.container.movieRepository
                HomeViewModel(repository = movieRepository)
            }
        }
    }

}