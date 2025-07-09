package com.silverkey.data.remote.mapper

import com.silverkey.data.remote.dto.ArticleDto
import com.silverkey.domain.model.Article

fun ArticleDto.toDomain(): Article {
    return Article(
        author = author ?: "Unknown Author",
        title = title ?: "",
        description = description ?: "",
        url = url ?: "",
        imageUrl = imageUrl ?: "",
        publishedAt = publishedAt ?: ""
    )
}