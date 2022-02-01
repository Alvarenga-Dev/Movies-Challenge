package com.alvarengadev.movieschallenge.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarengadev.movieschallenge.data.domain.Movie
import com.alvarengadev.movieschallenge.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    var movie: Movie? = null

    val statusGetMovieDetailsFromServer = repository.statusGetMovieDetailsFromServer

    val statusIsMovieInFavoritesFromDatabase = repository.statusIsMovieInFavoritesFromDatabase

    val statusInsertMovieInFavoriteFromDatabase = repository.statusInsertMovieInFavoriteFromDatabase

    val statusRemoveMovieInFavoritesFromDatabase = repository.statusRemoveMovieInFavoritesFromDatabase

    fun getMovieDetailsFromServer(movieId: Int) = viewModelScope.launch {
        repository.getMovieDetailsFromServer(movieId)
    }

    fun insertMovieInFavoriteFromDatabase(movie: Movie) = viewModelScope.launch {
        repository.insertMovieInFavoriteFromDatabase(movie)
    }

    fun isMovieInFavoritesFromDatabase(movieId: Int) = viewModelScope.launch {
        repository.isMovieInFavoritesFromDatabase(movieId)
    }

    fun removeMovieInFavoritesFromDatabase(movie: Movie) = viewModelScope.launch {
        repository.removeMovieInFavoritesFromDatabase(movie)
    }
}