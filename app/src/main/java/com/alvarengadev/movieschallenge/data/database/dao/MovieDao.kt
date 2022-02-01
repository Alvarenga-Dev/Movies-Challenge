package com.alvarengadev.movieschallenge.data.database.dao

import androidx.room.*
import com.alvarengadev.movieschallenge.data.database.entities.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity): Long

    @Delete
    suspend fun delete(movieEntity: MovieEntity): Int

    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE id =:id")
    suspend fun getMovie(id: Int): MovieEntity?
}