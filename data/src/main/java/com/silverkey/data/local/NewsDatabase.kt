package com.silverkey.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.silverkey.data.local.dao.NewsDao
import com.silverkey.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 2)
abstract class NewsDatabase :RoomDatabase(){
    abstract fun articleDao(): NewsDao
}