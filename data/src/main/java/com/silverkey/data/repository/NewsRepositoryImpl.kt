package com.silverkey.data.repository

import com.silverkey.data.remote.api.NewsApiService
import com.silverkey.data.remote.mapper.toDomain
import com.silverkey.domain.model.Article
import com.silverkey.domain.repository.NewsRepository
import com.silverkey.domain.utils.Result
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
) : NewsRepository {

    override suspend fun fetchNews(): Result<List<Article>> {
        return try {
            val response = newsApiService.getNews()
            val articles = response.articles?.map { it.toDomain() } ?: emptyList()

            if (articles.isEmpty()) {
                Result.Empty
            } else {
                Result.Success(articles)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}