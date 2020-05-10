package com.polish.currentnews.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    var id: String? = null,
    var name: String? = null
):Parcelable