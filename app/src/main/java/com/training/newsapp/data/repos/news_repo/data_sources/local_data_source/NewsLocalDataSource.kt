package com.training.newsapp.data.repos.news_repo.data_sources.local_data_source

import android.content.Context
import com.training.newsapp.data.api.model.Article
import com.training.newsapp.data.api.model.ArticleEntity
import com.training.newsapp.data.api.model.Source
import com.training.newsapp.data.database.MyDataBase
import javax.inject.Inject

class NewsLocalDataSource @Inject constructor(private val dataBase: MyDataBase) {


    suspend fun getSources(category: String): List<Source> {
        return dataBase.sourceDao().getSources(category)
    }

    suspend fun getArticles(sourceId:String): List<ArticleEntity> {
        return dataBase.sourceDao().getArticles(sourceId)
    }


    suspend fun saveSources(sources: List<Source>) {
        dataBase.sourceDao().insertSources(sources)
    }


    suspend fun saveArticles(articles: List<ArticleEntity>) {
        dataBase.sourceDao().insertArticles(articles)
    }





}