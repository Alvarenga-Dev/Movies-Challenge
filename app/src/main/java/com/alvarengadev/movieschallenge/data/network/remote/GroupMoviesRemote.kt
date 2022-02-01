package com.alvarengadev.movieschallenge.data.network.remote

import com.alvarengadev.movieschallenge.data.domain.Movie
import com.google.gson.annotations.SerializedName

data class GroupMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieResponse>
)

data class MovieResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: String?,
    @SerializedName("backdrop_path") val posterPrincipal: String?,
    @SerializedName("genres") val listGenres: List<GenresResponse>?,
)

fun MovieResponse.asDomain(): Movie = Movie(
    id,
    title,
    originalTitle,
    poster,
    overview,
    releaseDate,
    voteAverage,
    posterPrincipal,
    listGenres?.asDomain()?.joinToString(", ")
)

fun List<MovieResponse>.asDomain(): List<Movie> {
    return map {
        it.asDomain()
    }
}