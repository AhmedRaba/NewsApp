package com.training.newsapp.api

import com.training.newsapp.model.articles.ArticlesResponse
import com.training.newsapp.model.sources.SourcesResponse
import com.training.newsapp.utils.RetrofitClient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String = RetrofitClient.API_KEY
    ): Response<SourcesResponse>

    @GET("v2/everything")
    suspend fun getArticles(
        @Query("apikey") apiKey: String=RetrofitClient.API_KEY,
    ): Response<ArticlesResponse>

    @GET("v2/everything")
    suspend fun getArticlesBySource(
        @Query("apikey") apiKey: String=RetrofitClient.API_KEY,
        @Query("sources") sources: String
    ): Response<ArticlesResponse>


}