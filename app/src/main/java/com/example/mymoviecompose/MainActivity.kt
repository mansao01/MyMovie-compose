package com.example.mymoviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.mymoviecompose.ui.MovieApp
import com.example.mymoviecompose.ui.theme.MyMovieComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val application = application as MovieApplication
        val preferencesRepository = application.preferencesRepository
        super.onCreate(savedInstanceState)
        setContent {

            val isDarkMode = remember { mutableStateOf(false) }
            preferencesRepository.isDarkMode
                .collectAsState(initial = false)
                .value
                .also { collectedState ->
                    isDarkMode.value = collectedState
                }

            MyMovieComposeTheme(isDarkMode.value) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieApp(
                        onDarkModeChange = { newDarkMode ->
                            isDarkMode.value = newDarkMode
                        }
                    )
                }
            }
        }
    }
}

