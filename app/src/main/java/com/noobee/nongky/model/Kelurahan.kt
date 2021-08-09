package com.noobee.nongky.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Kelurahan(
    val label: String,
    val value: Int
): Parcelable