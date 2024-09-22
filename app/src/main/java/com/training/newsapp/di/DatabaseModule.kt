package com.training.newsapp.di

import android.content.Context
import androidx.room.Room
import com.training.newsapp.data.database.MyDataBase
import com.training.newsapp.data.dao.SourcesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): MyDataBase {
        return Room.databaseBuilder(
            context, MyDataBase::class.java,
            "my_database"
        ).build()
    }

//    @Provides
//    fun providesArticlesDao(dataBase: MyDataBase): ArticlesDao {
//        return dataBase.articleDao()
//    }

    @Provides
    fun providesSourcesDao(dataBase: MyDataBase): SourcesDao {
        return dataBase.sourceDao()
    }

}