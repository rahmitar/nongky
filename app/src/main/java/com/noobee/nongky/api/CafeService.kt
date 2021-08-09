package com.noobee.nongky.api

import com.noobee.nongky.model.GetDataCafeResponse
import com.noobee.nongky.model.GetDataDescCafeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CafeService {

    @GET("cafes/?limit=100")
    suspend fun getDataCafe(): Response<GetDataCafeResponse>

    @GET("cafes/desc/{id}")
    suspend fun getDescCafe(
        @Path("id") id: String
    ): Response<GetDataDescCafeResponse>

}