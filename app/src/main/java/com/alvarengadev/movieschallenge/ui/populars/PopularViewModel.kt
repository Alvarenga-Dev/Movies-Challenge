package com.alvarengadev.movieschallenge.ui.populars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alvarengadev.movieschallenge.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    fun getMoviesFromServer() = repository
        .getMoviesPopularFromServer()
        .cachedIn(viewModelScope)
}