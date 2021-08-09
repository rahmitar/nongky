package com.noobee.nongky.model

data class GetDataCafeResponse(
    val count: Int,
    val `data`: List<DataCafe>,
    val limit: Int,
    val page: Int,
    val status: Int
)