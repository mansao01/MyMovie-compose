package com.example.mymoviecompose.data

import com.example.mymoviecompose.network.ApiService
import com.example.mymoviecompose.network.response.DetailMovieResponse
import com.example.mymoviecompose.network.response.MovieResponse
import com.example.mymoviecompose.network.response.SearchMovieResponse
import com.example.mymoviecompose.network.response.TrendingMovieResponse
import retrofit2.http.Query

interface MovieRepository {
    suspend fun getMovies(): MovieResponse
    suspend fun getTrendingMovie(): TrendingMovieResponse
    suspend fun searchMovie(query: String):SearchMovieResponse
    suspend fun getMovieDetail(id: Int): DetailMovieResponse
}


class NetworkMovieRepository(
    private val apiService: ApiService
) : MovieRepository {
    override suspend fun getMovies(): MovieResponse {
        return apiService.getMovieList()
    }

    override suspend fun getTrendingMovie(): TrendingMovieResponse {
        return apiService.getTrendingMovies()
    }

    override suspend fun searchMovie(query: String): SearchMovieResponse {
        return apiService.searchMovie(query)
    }

    override suspend fun getMovieDetail(id: Int): DetailMovieResponse {
        return apiService.getDetailMovie(id)
    }

}