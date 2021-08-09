package com.noobee.nongky.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataDesc(
    var _id: String = "",
    var desc: String = "",
    val features: List<Feature>,
    var fk_cafe: String = ""
): Parcelable