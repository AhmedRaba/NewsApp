package com.silverkey.data.di

import android.content.Context
import com.silverkey.data.local.dao.NewsDao
import com.silverkey.data.remote.api.NewsApiService
import com.silverkey.data.repository.NewsRepositoryImpl
import com.silverkey.domain.repository.NewsRepository
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
    fun provideNewsRepository(
        api: NewsApiService,
        dao: NewsDao,
        @ApplicationContext context: Context
    ): NewsRepository = NewsRepositoryImpl(api,dao,context)
}
