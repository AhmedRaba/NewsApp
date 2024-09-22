package com.training.newsapp.data.repos

import android.content.Context
import com.training.newsapp.data.api.model.ArticlesResponse
import com.training.newsapp.data.api.model.SourcesResponse
import com.training.newsapp.isInternetAvailable
import com.training.newsapp.data.repos.news_repo.data_sources.local_data_source.NewsLocalDataSource
import com.training.newsapp.data.repos.news_repo.data_sources.remote_data_source.NewsRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val context: Context,
    private val localDataSource: NewsLocalDataSource,
    private val remoteDataSource: NewsRemoteDataSource,
) : NewsRepository {


    override suspend fun getSources(category: String): Response<SourcesResponse> {
        return if (isInternetAvailable(context)) {
            val response = remoteDataSource.getSources(category)
            if (response.isSuccessful) {
                response.body()?.let { sourcesResponse ->
                    localDataSource.saveSources(sourcesResponse.sources)
                }
            }
            response
        } else {
            val sources = localDataSource.getSources(category)
            Response.success(SourcesResponse(sources, ""))
        }
    }

    override suspend fun getArticlesBySource(
        source: String,
        query: String,
    ): Response<ArticlesResponse> {
        return remoteDataSource.getArticles(source)
    }
}
