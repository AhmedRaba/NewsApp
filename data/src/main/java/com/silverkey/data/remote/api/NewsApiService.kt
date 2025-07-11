package com.silverkey.data.remote.api

import com.silverkey.domain.utils.Constants.NEWS_SOURCE
import com.silverkey.data.BuildConfig
import com.silverkey.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("articles")
    suspend fun getNews(
        @Query("source") source: String = NEWS_SOURCE,
        @Query("apiKey") apiKey: String= BuildConfig.NEWS_API_KEY,
    ):NewsResponseDto

}