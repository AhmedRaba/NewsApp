package com.training.newsapp.data.api.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "articles")
@Parcelize
data class Article(

    @ColumnInfo @SerializedName("author") val author: String,

    @ColumnInfo @SerializedName("content") val content: String,

    @ColumnInfo @SerializedName("description") val description: String,

    @ColumnInfo @SerializedName("publishedAt") val publishedAt: String,

    @SerializedName("source") val source: Source,

    @ColumnInfo @SerializedName("title") val title: String,

    @ColumnInfo @PrimaryKey @SerializedName("url") val url: String,

    @ColumnInfo @SerializedName("urlToImage") val urlToImage: String,
) : Parcelable