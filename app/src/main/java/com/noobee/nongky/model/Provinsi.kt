package com.noobee.nongky.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Provinsi(
    val label: String,
    val value: Int
): Parcelable