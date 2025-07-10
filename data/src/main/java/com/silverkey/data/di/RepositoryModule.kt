package com.silverkey.data.di

import com.silverkey.data.remote.api.NewsApiService
import com.silverkey.data.repository.NewsRepositoryImpl
import com.silverkey.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        api: NewsApiService,
    ): NewsRepository = NewsRepositoryImpl(api)
}
