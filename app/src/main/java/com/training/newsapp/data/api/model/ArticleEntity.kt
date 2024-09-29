package com.training.newsapp.data.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    val author: String?,
    val content: String?,
    val description: String,
    val publishedAt: String,
    @ColumnInfo(name = "sourceName")
    val sourceName: String,
    val title: String,
    @PrimaryKey val url: String,
    val urlToImage: String,
)
