package com.example.mymoviecompose.ui.screen.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mymoviecompose.MovieApplication
import com.example.mymoviecompose.data.LocalMovieRepository
import com.example.mymoviecompose.data.MovieRepository
import com.example.mymoviecompose.data.local.model.Movie
import com.example.mymoviecompose.ui.common.DetailUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailViewModel(
    private val repository: MovieRepository,
    private val localMovieRepository: LocalMovieRepository
) : ViewModel() {

    var uiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set




    fun getDetailMovie(movieId: Int) {
        viewModelScope.launch {
            uiState = DetailUiState.Loading
            uiState = try {
                val result = repository.getMovieDetail(movieId)
                val localResult = localMovieRepository.getFavoriteById(movieId)
                DetailUiState.Success(result, localResult)
            } catch (e: IOException) {
                DetailUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                DetailUiState.Error(e.message.toString())
            }
        }

    }

    fun insertMovieToFavor(movie: Movie) {
        viewModelScope.launch {
            try {
                localMovieRepository.insert(movie)
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("DetailViewModel", e.message.toString())
            }
        }
    }

    fun deleteMovieFromFavor(movie: Movie) {
        viewModelScope.launch {
              try {
                localMovieRepository.delete(movie)
            } catch (e: IOException) {
                e.printStackTrace()
                DetailUiState.Error(e.message.toString())
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
                val movieRepository = application.container.movieRepository
                val localMovieRepository  = application.container.localMovieRepository
                DetailViewModel(repository = movieRepository, localMovieRepository = localMovieRepository)
            }
        }
    }
}