package com.silverkey.domain.repository

import com.silverkey.domain.model.Article
import com.silverkey.domain.utils.Result

interface NewsRepository {
    suspend fun fetchNews(): Result<List<Article>>

    suspend fun getSavedArticles(): Result<List<Article>>
    suspend fun saveArticle(article: Article)
    suspend fun deleteArticle(article: Article)
    suspend fun isArticleSaved(url: String): Boolean

}