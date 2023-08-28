package com.example.mymoviecompose

import android.app.Application
import com.example.mymoviecompose.data.AppContainer
import com.example.mymoviecompose.data.DefaultAppContainer

class MovieApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}