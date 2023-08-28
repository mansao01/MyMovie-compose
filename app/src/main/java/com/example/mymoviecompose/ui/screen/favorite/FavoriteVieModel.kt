package com.example.mymoviecompose.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mymoviecompose.MovieApplication
import com.example.mymoviecompose.data.LocalMovieRepository
import com.example.mymoviecompose.ui.common.FavoriteUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoriteVieModel(private val localMovieRepository: LocalMovieRepository) : ViewModel() {

    val favUiState:StateFlow<FavoriteUiState.Result> =
        localMovieRepository.getFavoriteMovie().map { FavoriteUiState.Result(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = FavoriteUiState.Result()
            )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
                val localMovieRepository = application.container.localMovieRepository
                FavoriteVieModel(localMovieRepository = localMovieRepository)
            }
        }
    }
}