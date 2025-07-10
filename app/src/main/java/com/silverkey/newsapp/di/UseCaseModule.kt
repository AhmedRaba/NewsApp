package com.silverkey.newsapp.di


import com.silverkey.domain.repository.NewsRepository
import com.silverkey.domain.usecase.FetchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideFetchNewsUseCase(
        repository: NewsRepository,
    ): FetchNewsUseCase = FetchNewsUseCase(repository)
}
