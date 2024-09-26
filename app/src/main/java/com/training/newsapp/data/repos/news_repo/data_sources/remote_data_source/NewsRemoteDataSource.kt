package com.training.newsapp.data.repos.news_repo.data_sources.remote_data_source

import android.util.Log
import com.training.newsapp.data.api.ApiService
import com.training.newsapp.data.api.model.ArticlesResponse
import com.training.newsapp.data.api.model.SourcesResponse
import retrofit2.Response
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val apiService: ApiService) {


    suspend fun getSources(category: String): Response<SourcesResponse> {
        val response = apiService.getSources(category = category)
        Log.e("NewsRemoteDataSource", "API Response: ${response.body()}")
        return response
    }

    suspend fun getArticles(source: String, query: String): Response<ArticlesResponse> {
        return apiService.getArticlesBySource(sources = source, query = query)
    }


}