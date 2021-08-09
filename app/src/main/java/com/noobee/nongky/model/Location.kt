package com.noobee.nongky.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val kecamatan: Kecamatan,
    val kelurahan: Kelurahan,
    val kota: Kota,
    val provinsi: Provinsi
): Parcelable