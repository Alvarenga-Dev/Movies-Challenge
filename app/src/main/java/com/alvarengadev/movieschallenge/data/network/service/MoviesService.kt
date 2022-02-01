package com.alvarengadev.movieschallenge.data.network.service

import com.alvarengadev.movieschallenge.data.network.remote.GroupMoviesResponse
import com.alvarengadev.movieschallenge.data.network.remote.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/popular")
    suspend fun getGroupMoviesPopulars(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int? = null
    ): Response<GroupMoviesResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>
}