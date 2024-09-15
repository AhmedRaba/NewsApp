package com.training.newsapp.repos

import com.training.newsapp.model.articles.ArticlesResponse
import com.training.newsapp.model.sources.SourcesResponse
import com.training.newsapp.utils.RetrofitClient
import retrofit2.Response

class NewsRepositoryImpl : NewsRepository {

    private val apiService = RetrofitClient.apiService


    override suspend fun getSources(): Response<SourcesResponse> {
        return apiService.getSources()
    }


    override suspend fun getArticles(): Response<ArticlesResponse> {
        return apiService.getArticles()
    }

}