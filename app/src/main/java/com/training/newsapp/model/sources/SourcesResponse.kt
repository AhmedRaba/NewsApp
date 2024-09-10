package com.training.newsapp.model.sources


import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)