package com.silverkey.data.di

import android.app.Application
import androidx.room.Room
import com.silverkey.data.local.dao.NewsDao
import com.silverkey.data.local.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app:Application): NewsDatabase {
        return Room.databaseBuilder(app, NewsDatabase::class.java,"news_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideArticleDao(db: NewsDatabase): NewsDao = db.articleDao()


}