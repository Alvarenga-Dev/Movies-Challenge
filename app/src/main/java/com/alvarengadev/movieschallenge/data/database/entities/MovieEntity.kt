package com.alvarengadev.movieschallenge.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alvarengadev.movieschallenge.data.domain.Movie

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String?,
    val originalTitle: String?,
    val poster: String?,
    val overview: String?,
    val releaseDate: String?,
    val voteAverage: String?,
    val posterPrincipal: String?,
    val listGenres: String?,
)

fun toEntity(movie: Movie): MovieEntity = MovieEntity(
    movie.id,
    movie.title,
    movie.originalTitle,
    movie.poster,
    movie.overview,
    movie.releaseDate,
    movie.voteAverage,
    movie.posterPrincipal,
    movie.listGenres
)

fun toDomain(movieEntity: MovieEntity): Movie = Movie(
    movieEntity.id,
    movieEntity.title,
    movieEntity.originalTitle,
    movieEntity.poster,
    movieEntity.overview,
    movieEntity.releaseDate,
    movieEntity.voteAverage,
    movieEntity.posterPrincipal,
    movieEntity.listGenres
)

fun List<MovieEntity>.asDomain(): List<Movie> {
    return map {
        toDomain(it)
    }
}