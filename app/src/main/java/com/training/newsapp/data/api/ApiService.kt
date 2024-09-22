package com.training.newsapp.data.api

import com.training.newsapp.data.api.model.ArticlesResponse
import com.training.newsapp.data.api.model.SourcesResponse
import com.training.newsapp.utils.RetrofitClient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String = RetrofitClient.API_KEY,
        @Query("category") category: String,
    ): Response<SourcesResponse>


    @GET("v2/top-headlines")
    suspend fun getArticlesBySource(
        @Query("apikey") apiKey: String=RetrofitClient.API_KEY,
        @Query("sources") sources: String,
        @Query("q") query: String=""
    ): Response<ArticlesResponse>


}