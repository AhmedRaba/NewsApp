package com.silverkey.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.silverkey.data.local.entity.ArticleEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)

    @Query("SELECT * FROM saved_articles")
    suspend fun getSavedArticles(): List<ArticleEntity>

    @Query("SELECT EXISTS(SELECT * FROM saved_articles WHERE url = :url)")
    suspend fun isArticleSaved(url: String): Boolean

}