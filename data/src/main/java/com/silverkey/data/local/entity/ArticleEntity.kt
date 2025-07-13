package com.silverkey.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    val url: String,
    val title: String,
    val author: String,
    val description: String,
    val imageUrl: String,
    val publishedAt: String,
    val localImagePath: String?

)