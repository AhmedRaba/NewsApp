package com.silverkey.domain.usecase

import com.silverkey.domain.repository.NewsRepository

class IsArticleSavedUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(url: String): Boolean {
        return repository.isArticleSaved(url)
    }
}
