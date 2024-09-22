package com.training.newsapp.di

import com.training.newsapp.data.api.ApiService
import com.training.newsapp.utils.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun providesApiService(): ApiService {
        return RetrofitClient.apiService

    }

}