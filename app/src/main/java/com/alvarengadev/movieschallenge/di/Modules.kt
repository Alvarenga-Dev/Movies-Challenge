package com.alvarengadev.movieschallenge.di

import android.content.Context
import androidx.room.Room
import com.alvarengadev.movieschallenge.data.database.MainDatabase
import com.alvarengadev.movieschallenge.data.network.ServiceProvider
import com.alvarengadev.movieschallenge.data.network.service.MoviesService
import com.alvarengadev.movieschallenge.data.repository.MoviesRepository
import com.alvarengadev.movieschallenge.data.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object ServiceModule {

    @Provides
    fun providerService(): MoviesService {
        return ServiceProvider.create(MoviesService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE = "main_database"

    @Singleton
    @Provides
    fun providerDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        MainDatabase::class.java,
        DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideDaoItem(database: MainDatabase) = database.movieDao()
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModules {

    @Binds
    abstract fun bindsMoviesRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ) : MoviesRepository
}