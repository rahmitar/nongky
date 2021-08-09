package com.noobee.nongky.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CAddress(
    val link: String,
    val location: Location,
    val name: String
): Parcelable