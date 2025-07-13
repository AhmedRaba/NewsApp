package com.silverkey.domain.usecase

import com.silverkey.domain.model.Article
import com.silverkey.domain.repository.NewsRepository
import com.silverkey.domain.utils.Result

class GetSavedArticlesUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(): Result<List<Article>> {
        return repository.getSavedArticles()
    }
}
