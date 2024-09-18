package com.training.newsapp.repos

import com.training.newsapp.model.articles.ArticlesResponse
import com.training.newsapp.model.sources.SourcesResponse
import retrofit2.Response

interface NewsRepository {

    suspend fun getSources(): Response<SourcesResponse>

    suspend fun getArticles(): Response<ArticlesResponse>

    suspend fun getArticlesBySource(source: String): Response<ArticlesResponse>

}