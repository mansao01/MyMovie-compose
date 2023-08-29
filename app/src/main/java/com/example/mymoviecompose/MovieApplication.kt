package com.example.mymoviecompose

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.mymoviecompose.data.AppContainer
import com.example.mymoviecompose.data.DefaultAppContainer
import com.example.mymoviecompose.data.PreferencesRepository

private const val THEME_PREFERENCES_NAME = "theme_preferences_name"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = THEME_PREFERENCES_NAME
)

class MovieApplication : Application() {
    lateinit var container: AppContainer
    lateinit var preferencesRepository: PreferencesRepository
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
        preferencesRepository = PreferencesRepository(dataStore)
    }
}