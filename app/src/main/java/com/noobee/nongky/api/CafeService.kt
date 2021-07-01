package com.noobee.nongky.api

import com.noobee.nongky.model.GetDataCafeResponse
import retrofit2.Response
import retrofit2.http.GET

interface CafeService {
    @GET("cafes")
    suspend fun getDataCafe(): Response<GetDataCafeResponse>

}