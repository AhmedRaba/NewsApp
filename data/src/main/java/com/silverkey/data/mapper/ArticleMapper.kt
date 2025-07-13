package com.silverkey.data.mapper

import com.silverkey.data.local.entity.ArticleEntity
import com.silverkey.data.remote.dto.ArticleDto
import com.silverkey.domain.model.Article

fun ArticleDto.toDomain(): Article {
    return Article(
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        publishedAt = publishedAt,
        localImagePath = null
    )
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        publishedAt = publishedAt,
        localImagePath = null
    )
}

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        publishedAt = publishedAt,
        localImagePath = null
    )
}