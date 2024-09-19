package com.training.newsapp.repos

import com.training.newsapp.model.articles.ArticlesResponse
import com.training.newsapp.model.sources.SourcesResponse
import com.training.newsapp.utils.RetrofitClient
import retrofit2.Response

class NewsRepositoryImpl : NewsRepository {

    private val apiService = RetrofitClient.apiService


    override suspend fun getSources(category:String): Response<SourcesResponse> {
        return apiService.getSources(category= category)
    }


    override suspend fun getArticles(): Response<ArticlesResponse> {
        return apiService.getArticles()
    }

    override suspend fun getArticlesBySource(source: String): Response<ArticlesResponse> {
        return apiService.getArticlesBySource(sources = source)
    }

}