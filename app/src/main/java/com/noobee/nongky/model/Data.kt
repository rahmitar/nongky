package com.noobee.nongky.model

data class Data(
    val _id: String,
    val c_address: CAddress,
    val c_category: String,
    val c_contact: CContact,
    val c_coordinate: CCoordinate,
    val c_name: String,
    val c_profile: String,
    val c_tags: List<String>,
    val c_year: Int,
    val created_at: String,
    val fk_auth: String,
    val updated_at: String,
    var c_distance: Double?
)