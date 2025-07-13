package com.silverkey.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.silverkey.data.local.dao.NewsDao
import com.silverkey.data.remote.api.NewsApiService
import com.silverkey.data.mapper.toDomain
import com.silverkey.data.mapper.toEntity
import com.silverkey.data.utils.FileUtils
import com.silverkey.domain.model.Article
import com.silverkey.domain.repository.NewsRepository
import com.silverkey.domain.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsDao: NewsDao,
    @ApplicationContext private val context: Context
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

    override suspend fun getSavedArticles(): Result<List<Article>> {
        return try {
            val saved = newsDao.getSavedArticles().map { it.toDomain() }
            if (saved.isEmpty()) Result.Empty else Result.Success(saved)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveArticle(article: Article) {
        try {
            val localImagePath = article.imageUrl?.let {
                FileUtils.downloadImageAndSaveToInternalStorage(context, it)
            }

            val updatedArticle = article.copy(localImagePath = localImagePath)
            newsDao.insertArticle(updatedArticle.toEntity())

        } catch (e: Exception) {
            Log.e("NewsRepositoryImpl", "Error saving article: ${e.message}")
        }
    }

    override suspend fun deleteArticle(article: Article) {
        try {
            article.localImagePath?.let {
                try {
                    File(it).delete()
                } catch (fileError: Exception) {
                    Log.e("NewsRepositoryImpl", "Error deleting image: ${fileError.message}")
                }
            }

            newsDao.deleteArticle(article.toEntity())

        } catch (e: Exception) {
            Log.e("NewsRepositoryImpl", "Error deleting article: ${e.message}")
        }
    }

    override suspend fun isArticleSaved(url: String): Boolean {
        return try {
            newsDao.isArticleSaved(url)
        } catch (e: Exception) {
            false
        }
    }
}