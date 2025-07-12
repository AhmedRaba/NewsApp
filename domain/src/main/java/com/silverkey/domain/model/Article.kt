package com.silverkey.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val author:String,
    val title:String,
    val description:String,
    val url:String,
    val imageUrl:String,
    val publishedAt:String,
):Parcelable
