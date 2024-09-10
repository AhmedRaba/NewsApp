package com.training.newsapp.api

import com.training.newsapp.model.articles.ArticlesResponse
import com.training.newsapp.model.sources.SourcesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("v2/top-headlines?sources=techcrunch&apiKey=6fc4d0a939e44cbba94469ab99730dd0")
    suspend fun getSources(): Response<SourcesResponse>

    @GET("v2/everything?apiKey=6fc4d0a939e44cbba94469ab99730dd0&sources=abc-news")
    suspend fun getArticles(): Response<ArticlesResponse>


}