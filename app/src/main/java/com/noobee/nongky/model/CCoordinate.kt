package com.noobee.nongky.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CCoordinate(
    val lat: Double,
    val lon: Double
) : Parcelable