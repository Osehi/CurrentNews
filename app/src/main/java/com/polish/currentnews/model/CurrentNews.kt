package com.polish.currentnews.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentNews(
    var articles: List<Article?>? = null,
    var status: String? = null,
    var totalResults: Int? = null
):Parcelable