package com.training.newsapp.data.repos.news_repo.data_sources.remote_data_source

import com.training.newsapp.data.api.ApiService
import com.training.newsapp.data.api.model.ArticlesResponse
import com.training.newsapp.data.api.model.SourcesResponse
import retrofit2.Response
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val apiService: ApiService) {


    suspend fun getSources(category: String): Response<SourcesResponse> {
        return apiService.getSources(category = category)
    }

    suspend fun getArticles(source: String): Response<ArticlesResponse> {
        return apiService.getArticlesBySource(sources = source)
    }


}