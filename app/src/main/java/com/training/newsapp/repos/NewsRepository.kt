package com.training.newsapp.repos

import com.training.newsapp.model.articles.ArticlesResponse
import com.training.newsapp.model.sources.SourcesResponse
import retrofit2.Response

interface NewsRepository {

    suspend fun getSources(category:String): Response<SourcesResponse>

    suspend fun getArticles(): Response<ArticlesResponse>

    suspend fun getArticlesBySource(source: String,query: String): Response<ArticlesResponse>

}