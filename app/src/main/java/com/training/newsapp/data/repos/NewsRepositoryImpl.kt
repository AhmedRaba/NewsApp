package com.training.newsapp.data.repos

import android.content.Context
import android.util.Log
import com.training.newsapp.Resource
import com.training.newsapp.data.api.model.ArticlesResponse
import com.training.newsapp.data.api.model.SourcesResponse
import com.training.newsapp.data.api.model.toApiModel
import com.training.newsapp.data.api.model.toEntity
import com.training.newsapp.data.repos.news_repo.data_sources.local_data_source.NewsLocalDataSource
import com.training.newsapp.data.repos.news_repo.data_sources.remote_data_source.NewsRemoteDataSource
import com.training.newsapp.isInternetAvailable
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val context: Context,
    private val localDataSource: NewsLocalDataSource,
    private val remoteDataSource: NewsRemoteDataSource,
) : NewsRepository {


    override suspend fun getSources(category: String): Resource<SourcesResponse> {
        return if (isInternetAvailable(context)) {
            val response = remoteDataSource.getSources(category)
            if (response.isSuccessful) {
                response.body()?.let { sourcesResponse ->
                    localDataSource.saveSources(sourcesResponse.sources)
                    Resource.Success(sourcesResponse)
                } ?: Resource.Error("Received empty response body")
            } else {
                Log.e("getSources", "getSources: NOT SUCCESSFUL ${response.code()}")
                Resource.Error("Error: ${response.message()}")
            }
        } else {
            val sources = localDataSource.getSources(category)
            Resource.Success(SourcesResponse(sources, ""))
        }
    }


    override suspend fun getArticlesBySource(
        source: String,
        query: String,
    ): Resource<ArticlesResponse> {
        return if (isInternetAvailable(context)) {
            val response = remoteDataSource.getArticles(source, query)
            if (response.isSuccessful) {
                response.body()?.let { articlesResponse ->
                    val articlesEntity = articlesResponse.articles.map { it.toEntity() }
                    localDataSource.saveArticles(articlesEntity)
                    Resource.Success(articlesResponse)
                } ?: Resource.Error("Empty Received empty response body")
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } else {
            val articles = localDataSource.getArticles(formatSourceName(source))
            val articlesApiModels = articles.map { it.toApiModel() }
            Log.d("getArticlesBySource", "getArticlesBySource: ${articlesApiModels.size}")
            Log.d("getArticlesBySource", "getArticlesBySource: ${formatSourceName(source)}")
            if (articlesApiModels.isEmpty()) {
                Resource.Error("No internet connection")
            } else {
                Resource.Success(ArticlesResponse(articlesApiModels, "", articlesApiModels.size))
            }
        }
    }
}

private fun formatSourceName(sourceName: String): String {
    return sourceName.lowercase().replace("-", " ")
}

