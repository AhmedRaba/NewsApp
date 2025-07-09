package com.silverkey.data.remote.api

import com.silverkey.core.utils.Constants.NEWS_SOURCE
import com.silverkey.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("articles")
    suspend fun getTopArticles(
        @Query("source") source: String = NEWS_SOURCE,
        @Query("apiKey") apiKey: String,
    ):NewsResponseDto

}