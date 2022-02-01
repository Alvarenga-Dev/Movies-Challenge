package com.alvarengadev.movieschallenge.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.alvarengadev.movieschallenge.BuildConfig
import com.alvarengadev.movieschallenge.data.database.dao.MovieDao
import com.alvarengadev.movieschallenge.data.database.entities.asDomain
import com.alvarengadev.movieschallenge.data.database.entities.toEntity
import com.alvarengadev.movieschallenge.data.domain.Movie
import com.alvarengadev.movieschallenge.data.network.remote.asDomain
import com.alvarengadev.movieschallenge.data.network.service.MoviesService
import com.alvarengadev.movieschallenge.data.repository.paging.MoviesPopularDataSource
import com.alvarengadev.movieschallenge.utils.remote.ResultUtils
import com.alvarengadev.movieschallenge.utils.format.codeResponseToString
import com.alvarengadev.movieschallenge.utils.format.errorMoviesFavoritesToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val service: MoviesService,
    private val dao: MovieDao
) : MoviesRepository {

    private val _statusGetMovieDetailsFromServer = MutableLiveData<ResultUtils<Movie>>()
    override val statusGetMovieDetailsFromServer: LiveData<ResultUtils<Movie>>
        get() = _statusGetMovieDetailsFromServer

    private val _statusGetMoviesFavoritesFromDatabase = MutableLiveData<ResultUtils<List<Movie>>>()
    override val statusGetMoviesFavoritesFromDatabase: LiveData<ResultUtils<List<Movie>>>
        get() = _statusGetMoviesFavoritesFromDatabase

    private val _statusIsMovieInFavoritesFromDatabase = MutableLiveData<ResultUtils<Boolean>>()
    override val statusIsMovieInFavoritesFromDatabase: LiveData<ResultUtils<Boolean>>
        get() = _statusIsMovieInFavoritesFromDatabase

    private val _statusInsertMovieInFavoriteFromDatabase = MutableLiveData<ResultUtils<Boolean>>()
    override val statusInsertMovieInFavoriteFromDatabase: LiveData<ResultUtils<Boolean>>
        get() = _statusInsertMovieInFavoriteFromDatabase

    private val _statusRemoveMovieInFavoritesFromDatabase = MutableLiveData<ResultUtils<Boolean>>()
    override val statusRemoveMovieInFavoritesFromDatabase: LiveData<ResultUtils<Boolean>>
        get() = _statusRemoveMovieInFavoritesFromDatabase

    override fun getMoviesPopularFromServer() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            maxSize = MAX_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            MoviesPopularDataSource(
                API_KEY,
                service
            )
        }
    ).liveData

    override suspend fun getMovieDetailsFromServer(movieId: Int) {
        withContext(Dispatchers.IO) {
            _statusGetMovieDetailsFromServer.postValue(ResultUtils.loading())
            try {
                val response = service.getMovieDetails(movieId, API_KEY)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _statusGetMovieDetailsFromServer.postValue(
                            ResultUtils.success(it.asDomain())
                        )
                    }
                } else {
                    _statusGetMovieDetailsFromServer.postValue(
                        ResultUtils.error(response.message())
                    )
                }

            } catch (ex: Exception) {
                ResultUtils.error(ex.message, null)
            }
        }
    }

    override suspend fun getMoviesFavoritesFromDatabase() {
        withContext(Dispatchers.IO) {
            _statusGetMoviesFavoritesFromDatabase.postValue(ResultUtils.loading())
            try {
                val moviesFavorites = dao.getAll()

                if (moviesFavorites.isNotEmpty()) {
                    _statusGetMoviesFavoritesFromDatabase.postValue(
                        ResultUtils.success(moviesFavorites.asDomain())
                    )
                } else {
                    _statusGetMoviesFavoritesFromDatabase.postValue(
                        ResultUtils.error(errorMoviesFavoritesToString(true))
                    )
                }
            } catch (ex: Exception) {
                _statusGetMoviesFavoritesFromDatabase.postValue(
                    ResultUtils.error(
                        errorMoviesFavoritesToString(),
                        null
                    )
                )
            }
        }
    }

    override suspend fun insertMovieInFavoriteFromDatabase(movie: Movie) {
        _statusInsertMovieInFavoriteFromDatabase.postValue(ResultUtils.loading())
        withContext(Dispatchers.IO) {
            try {
                val rowInserted = dao.insert(
                    toEntity(movie)
                )
                _statusInsertMovieInFavoriteFromDatabase.postValue(
                    if (rowInserted != -1L)
                        ResultUtils.success(true)
                    else
                        ResultUtils.error(null, false)
                )
            } catch (ex: Exception) {
                _statusInsertMovieInFavoriteFromDatabase.postValue(
                    ResultUtils.error(
                        ex.message,
                        false
                    )
                )
            }
        }
    }

    override suspend fun isMovieInFavoritesFromDatabase(movieId: Int) {
        withContext(Dispatchers.IO) {
            _statusIsMovieInFavoritesFromDatabase.postValue(ResultUtils.loading())
            try {
                val movie = dao.getMovie(movieId)
                _statusIsMovieInFavoritesFromDatabase.postValue(
                    ResultUtils.success(
                        movie != null
                    )
                )
            } catch (ex: Exception) {
                _statusIsMovieInFavoritesFromDatabase.postValue(
                    ResultUtils.error(ex.message, null)
                )
            }
        }
    }

    override suspend fun removeMovieInFavoritesFromDatabase(movie: Movie) {
        withContext(Dispatchers.IO) {
            _statusRemoveMovieInFavoritesFromDatabase.postValue(ResultUtils.loading())
            try {
                val rowDeleted = dao.delete(
                    toEntity(movie)
                )
                _statusRemoveMovieInFavoritesFromDatabase.postValue(
                    if (rowDeleted != -1)
                        ResultUtils.success(true)
                    else
                        ResultUtils.error(null, false)
                )
            } catch (ex: Exception) {
                _statusRemoveMovieInFavoritesFromDatabase.postValue(
                    ResultUtils.error(
                        ex.message,
                        null
                    )
                )
            }

        }
    }

    companion object {
        const val API_KEY = BuildConfig.TMDB_TOKEN
        const val PAGE_SIZE = 10
        const val MAX_SIZE = 100
    }
}