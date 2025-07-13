package com.silverkey.domain.usecase

import com.silverkey.domain.model.Article
import com.silverkey.domain.repository.NewsRepository

class DeleteArticleUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(article: Article) {
        repository.deleteArticle(article)
    }
}
