package com.alvarengadev.movieschallenge.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alvarengadev.movieschallenge.data.domain.Movie
import com.alvarengadev.movieschallenge.data.network.remote.asDomain
import com.alvarengadev.movieschallenge.data.network.service.MoviesService
import com.alvarengadev.movieschallenge.utils.exceptions.ResponseErrorHttpException
import com.alvarengadev.movieschallenge.utils.exceptions.ResponseNoBodyException
import retrofit2.HttpException

private const val TMDB_STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 10

class MoviesPopularDataSource(
    private val apiKey: String,
    private val service: MoviesService
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = service.getGroupMoviesPopulars(apiKey, pageIndex)
            val results = response.body()?.results?.asDomain()
            val nextKey = if (results?.isEmpty() == true) null else pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)

            results?.let {
                LoadResult.Page(
                    data = it,
                    prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                    nextKey = nextKey
                )
            } ?: throw ResponseNoBodyException()
        } catch (ex: HttpException) {
            LoadResult.Error(
                ResponseErrorHttpException(
                    ex.code()
                )
            )
        } catch (ex: Exception) {
            LoadResult.Error(
                ResponseErrorHttpException()
            )
        }
    }
}