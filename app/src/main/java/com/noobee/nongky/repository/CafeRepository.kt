package com.noobee.nongky.repository

import com.noobee.nongky.api.CafeService
import com.noobee.nongky.model.GetDataCafeResponse
import com.noobee.nongky.util.Resource
import retrofit2.Response
import javax.inject.Inject

class CafeRepository @Inject constructor(private val cafeService: CafeService) {
    suspend fun getDataCafe(): Resource<GetDataCafeResponse>{
        cafeService.getDataCafe().let { response ->
            if (response.isSuccessful){
                response.body()?.let {
                    return Resource.Success(it)
                }
            }

            return Resource.Error(response.message())
        }
    }

}