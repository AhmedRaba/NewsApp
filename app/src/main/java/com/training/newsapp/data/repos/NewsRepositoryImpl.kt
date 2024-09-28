package com.training.newsapp.data.repos

import android.content.Context
import android.util.Log
import com.training.newsapp.Resource
import com.training.newsapp.data.api.model.ArticlesResponse
import com.training.newsapp.data.api.model.SourcesResponse
import com.training.newsapp.data.repos.news_repo.data_sources.local_data_source.NewsLocalDataSource
import com.training.newsapp.data.repos.news_repo.data_sources.remote_data_source.NewsRemoteDataSource
import com.training.newsapp.isInternetAvailable
import retrofit2.Response
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
                    Resource.Success(sourcesResponse) // Return success with data
                } ?: Resource.Error("Received empty response body") // Handle null body
            } else {
                Log.e("getSources", "getSources: NOT SUCCESSFUL ${response.code()}")
                Resource.Error("Error: ${response.message()}") // Return error with message
            }
        } else {
            val sources = localDataSource.getSources(category)
            Resource.Success(SourcesResponse(sources, "")) // Return local data as success
        }
    }


    override suspend fun getArticlesBySource(source: String, query: String): Resource<ArticlesResponse> {
        return try {
            val response = remoteDataSource.getArticles(source, query)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error("Failed to fetch articles: ${e.message}")
        }
    }
}
