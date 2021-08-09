package com.noobee.nongky.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Feature(
    val icon: String,
    val name: String
) : Parcelable