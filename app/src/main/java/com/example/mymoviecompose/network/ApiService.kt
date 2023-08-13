package com.example.mymoviecompose.network

import com.example.mymoviecompose.network.response.DetailMovieResponse
import com.example.mymoviecompose.network.response.MovieResponse
import com.example.mymoviecompose.network.response.SearchMovieResponse
import com.example.mymoviecompose.network.response.TrendingMovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("discover/movie")
    suspend fun getMovieList(
//        @Query("api_key")
//        apiKey: String = "753e9830cd4e603ae28d706973ed36dd"
        @Header("Authorization")
        token:String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3NTNlOTgzMGNkNGU2MDNhZTI4ZDcwNjk3M2VkMzZkZCIsInN1YiI6IjYyYTAwYWI5MTEzMGJkMDA5ZmM4NTAyMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.YArQOTxSOwqjeEJd7Cj7kqQ1KtMWzKYhFVLUegkPNY0"
    ): MovieResponse

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key")
        apiKey: String = "753e9830cd4e603ae28d706973ed36dd"
    ):TrendingMovieResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query")
        query: String,
        @Query("api_key")
        apiKey: String = "753e9830cd4e603ae28d706973ed36dd",
        @Query("include_adult")
        includeAdult:Boolean = true
    ):SearchMovieResponse

    @GET("movie/{movie_id}")
     suspend fun getDetailMovie(
        @Path("movie_id")
        movieId: Int,
        @Query("api_key")
        apiKey: String = "753e9830cd4e603ae28d706973ed36dd"
    ): DetailMovieResponse
}