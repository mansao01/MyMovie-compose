package com.example.mymoviecompose.data

import com.example.mymoviecompose.data.local.MovieDao
import com.example.mymoviecompose.data.local.model.Movie
import com.example.mymoviecompose.data.network.ApiService
import com.example.mymoviecompose.data.network.response.DetailMovieResponse
import com.example.mymoviecompose.data.network.response.MovieResponse
import com.example.mymoviecompose.data.network.response.SearchMovieResponse
import com.example.mymoviecompose.data.network.response.TrendingMovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(): MovieResponse
    suspend fun getTrendingMovie(): TrendingMovieResponse
    suspend fun searchMovie(query: String): SearchMovieResponse
    suspend fun getMovieDetail(id: Int): DetailMovieResponse
}

interface LocalMovieRepository {
    suspend fun insert(movie: Movie)
    suspend fun delete(movie: Movie)
    suspend fun getFavoriteMovie(): Flow<List<Movie>>
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

class RoomLocalMovieRepository(
    private val movieDao: MovieDao
) : LocalMovieRepository {
    override suspend fun insert(movie: Movie) = movieDao.insert(movie)

    override suspend fun delete(movie: Movie) = movieDao.delete(movie)

    override suspend fun getFavoriteMovie(): Flow<List<Movie>> = movieDao.getFavorite()

}