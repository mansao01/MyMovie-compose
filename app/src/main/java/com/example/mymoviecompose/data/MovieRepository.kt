package com.example.mymoviecompose.data

import com.example.mymoviecompose.network.ApiService
import com.example.mymoviecompose.network.response.DetailMovieResponse
import com.example.mymoviecompose.network.response.MovieResponse

interface MovieRepository {
    suspend fun getMovies(): MovieResponse
    suspend fun getMovieDetail(id:Int): DetailMovieResponse
}


class NetworkMovieRepository(
    private val apiService: ApiService
) : MovieRepository {
    override suspend fun getMovies(): MovieResponse {
        return apiService.getMovieList()
    }

    override suspend fun getMovieDetail(id:Int): DetailMovieResponse {
        return apiService.getDetailMovie(id)
    }

}