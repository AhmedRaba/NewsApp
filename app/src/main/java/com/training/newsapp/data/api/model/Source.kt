package com.training.newsapp.data.api.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity(tableName = "sources")
@Parcelize
data class Source(
    @ColumnInfo
    @SerializedName("category")
    val category: String,

    @SerializedName("country")
    val country: String,

    @ColumnInfo
    @SerializedName("description")
    val description: String,

    @ColumnInfo
    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @ColumnInfo
    @SerializedName("language")
    val language: String,

    @ColumnInfo
    @SerializedName("name")
    val name: String,

    @ColumnInfo
    @SerializedName("url")
    val url: String,
) : Parcelable