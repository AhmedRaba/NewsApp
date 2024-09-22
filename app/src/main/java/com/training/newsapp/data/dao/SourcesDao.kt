package com.training.newsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.training.newsapp.data.api.model.Source


@Dao
interface SourcesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(sources: List<Source>)

    @Query("SELECT * FROM sources WHERE category = :categoryId")
    suspend fun getSources(categoryId:String):List<Source>

}