package com.noobee.nongky.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DataFirebase(
    var cid: String? = "",
    var c_name: String? = "",
    var c_address: String? = "",
    var lat: Double? = 0.0,
    var long: Double? = 0.0,
    var cafes: MutableMap<String, Boolean> = HashMap()
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "cid" to cid,
            "c_name" to c_name,
            "c_address" to c_address
        )
    }
}