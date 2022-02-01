package com.alvarengadev.movieschallenge.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.alvarengadev.movieschallenge.data.domain.Movie
import com.alvarengadev.movieschallenge.utils.remote.ResultUtils

interface MoviesRepository {

    val statusGetMovieDetailsFromServer: LiveData<ResultUtils<Movie>>
    val statusGetMoviesFavoritesFromDatabase: LiveData<ResultUtils<List<Movie>>>
    val statusInsertMovieInFavoriteFromDatabase: LiveData<ResultUtils<Boolean>>
    val statusIsMovieInFavoritesFromDatabase: LiveData<ResultUtils<Boolean>>
    val statusRemoveMovieInFavoritesFromDatabase: LiveData<ResultUtils<Boolean>>

    fun getMoviesPopularFromServer(): LiveData<PagingData<Movie>>

    suspend fun getMovieDetailsFromServer(movieId: Int)

    suspend fun isMovieInFavoritesFromDatabase(movieId: Int)

    suspend fun getMoviesFavoritesFromDatabase()

    suspend fun insertMovieInFavoriteFromDatabase(movie: Movie)

    suspend fun removeMovieInFavoritesFromDatabase(movie: Movie)
}