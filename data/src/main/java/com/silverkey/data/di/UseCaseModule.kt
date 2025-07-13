package com.silverkey.data.di


import com.silverkey.domain.repository.NewsRepository
import com.silverkey.domain.usecase.DeleteArticleUseCase
import com.silverkey.domain.usecase.FetchNewsUseCase
import com.silverkey.domain.usecase.GetSavedArticlesUseCase
import com.silverkey.domain.usecase.IsArticleSavedUseCase
import com.silverkey.domain.usecase.SaveArticleUseCase
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

    @Provides
    @Singleton
    fun provideSaveArticleUseCase(
        repository: NewsRepository,
    ): SaveArticleUseCase = SaveArticleUseCase(repository)

    @Provides
    @Singleton
    fun provideIsArticleSavedUseCase(
        repository: NewsRepository,
    ): IsArticleSavedUseCase = IsArticleSavedUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSavedArticlesUseCase(
        repository: NewsRepository,
    ): GetSavedArticlesUseCase = GetSavedArticlesUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteArticleUseCase(
        repository: NewsRepository,
    ): DeleteArticleUseCase = DeleteArticleUseCase(repository)
}
