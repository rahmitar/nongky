package com.noobee.nongky.model

data class GetDataCafeResponse(
    val count: Int,
    val `data`: List<Data>,
    val limit: Int,
    val page: Int,
    val status: Int
)