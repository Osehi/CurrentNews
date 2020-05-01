package com.polish.currentnews.model


import com.google.gson.annotations.SerializedName

data class CurrentNews(
    var articles: List<Article?>? = null,
    var status: String? = null,
    var totalResults: Int? = null
)