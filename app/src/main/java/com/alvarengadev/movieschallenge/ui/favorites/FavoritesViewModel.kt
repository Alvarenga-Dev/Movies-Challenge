package com.alvarengadev.movieschallenge.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarengadev.movieschallenge.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    val statusGetMoviesFavoritesFromDatabase = repository.statusGetMoviesFavoritesFromDatabase

    fun getMoviesFavoritesFromDatabase() = viewModelScope.launch {
        repository.getMoviesFavoritesFromDatabase()
    }
}