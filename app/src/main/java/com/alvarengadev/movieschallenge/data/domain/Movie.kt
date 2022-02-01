package com.alvarengadev.movieschallenge.data.domain

data class Movie(
    val id: Int,
    val title: String?,
    val originalTitle: String?,
    val poster: String?,
    val overview: String?,
    val releaseDate: String?,
    val voteAverage: String?,
    val posterPrincipal: String?,
    val listGenres: String?,
)