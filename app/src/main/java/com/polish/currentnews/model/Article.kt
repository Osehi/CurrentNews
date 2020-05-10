package com.polish.currentnews.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable
@Parcelize
data class Article(
    var author: String? = null,
    var content: String? = null,
    var description: String? = null,
    var publishedAt: String? = null,
    var source:@RawValue Source? = null,
    var title: String? = null,
    var url: String? = null,
    var urlToImage: String? = null
) :Parcelable