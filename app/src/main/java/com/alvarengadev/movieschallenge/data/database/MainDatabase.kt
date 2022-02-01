package com.alvarengadev.movieschallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alvarengadev.movieschallenge.data.database.dao.MovieDao
import com.alvarengadev.movieschallenge.data.database.entities.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}