package com.training.newsapp.data.api.model


fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        sourceName = this.source.name.lowercase(),
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage ?: ""
    )
}

fun ArticleEntity.toApiModel(): Article {
    return Article(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        source = Source(
            category = "Default Category",
            country = "Default Country",
            description = "Default Description",
            id = this.sourceName,
            language = "Default Language",
            name = this.sourceName,
            url = "Default URL"
        ),
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage
    )
}