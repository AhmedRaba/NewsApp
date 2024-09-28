package com.training.newsapp.data.repos

import com.training.newsapp.Resource
import com.training.newsapp.data.api.model.ArticlesResponse
import com.training.newsapp.data.api.model.SourcesResponse
import com.training.newsapp.data.repos.news_repo.data_sources.local_data_source.NewsLocalDataSource
import retrofit2.Response

interface NewsRepository {



    suspend fun getSources(category:String): Resource<SourcesResponse>


    suspend fun getArticlesBySource(source: String,query: String): Resource<ArticlesResponse>

}