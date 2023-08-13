package com.example.mymoviecompose.ui.screen.search

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
import com.example.mymoviecompose.ui.common.SearchUiState
import com.example.mymoviecompose.ui.screen.home.HomeViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SearchViewModel(private val repository: MovieRepository) : ViewModel() {
    val greeting by mutableStateOf("SUPPP")
    var uiState: SearchUiState by mutableStateOf(SearchUiState.Loading)
        private set


    fun searchMovie(query: String) {
        viewModelScope.launch {
            uiState = SearchUiState.Loading
            uiState = try {
                val result = repository.searchMovie(query)
                SearchUiState.Success(result)
            } catch (e: IOException) {
                SearchUiState.Error
            } catch (e: HttpException) {
                SearchUiState.Error
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
                val movieRepository = application.container.movieRepository
                SearchViewModel(repository = movieRepository)
            }
        }
    }
}