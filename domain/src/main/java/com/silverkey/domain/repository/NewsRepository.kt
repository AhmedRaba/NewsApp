package com.silverkey.domain.repository

import com.silverkey.domain.model.Article
import com.silverkey.domain.utils.Result

interface NewsRepository {
    suspend fun fetchNews(): Result<List<Article>>
}