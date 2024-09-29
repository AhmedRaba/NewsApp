package com.training.newsapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.training.newsapp.data.api.model.Article
import com.training.newsapp.data.api.model.ArticleEntity
import com.training.newsapp.data.api.model.Source
import com.training.newsapp.data.dao.SourcesDao

@Database(entities = [Source::class,ArticleEntity::class], version = 1)
abstract class MyDataBase : RoomDatabase() {

    abstract fun sourceDao(): SourcesDao

}