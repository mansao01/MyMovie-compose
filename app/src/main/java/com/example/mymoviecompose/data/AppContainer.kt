package com.example.mymoviecompose.data

import android.content.Context
import com.example.mymoviecompose.data.local.MovieDatabase
import com.example.mymoviecompose.data.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val movieRepository: MovieRepository
    val localMovieRepository: LocalMovieRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    private val baseUrl = "https://api.themoviedb.org/3/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()


    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    override val movieRepository: MovieRepository by lazy {
        NetworkMovieRepository(retrofitService)
    }
    override val localMovieRepository: LocalMovieRepository by lazy {
        RoomLocalMovieRepository(MovieDatabase.getDatabase(context).movieDao())
    }

}