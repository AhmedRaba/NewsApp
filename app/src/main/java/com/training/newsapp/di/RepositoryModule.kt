package com.training.newsapp.di

import android.content.Context
import com.training.newsapp.data.api.ApiService
import com.training.newsapp.data.database.MyDataBase
import com.training.newsapp.data.repos.NewsRepository
import com.training.newsapp.data.repos.NewsRepositoryImpl
import com.training.newsapp.data.repos.news_repo.data_sources.local_data_source.NewsLocalDataSource
import com.training.newsapp.data.repos.news_repo.data_sources.remote_data_source.NewsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideLocalDataSource(database: MyDataBase): NewsLocalDataSource {
        return NewsLocalDataSource(database)
    }

    @Provides
    @Singleton
    fun providesNewsRepository(
        apiService: ApiService,
        localDataSource: NewsLocalDataSource,
        remoteDataSource: NewsRemoteDataSource,
        @ApplicationContext context: Context
    ): NewsRepository {
        return NewsRepositoryImpl(context, localDataSource,remoteDataSource)
    }


}