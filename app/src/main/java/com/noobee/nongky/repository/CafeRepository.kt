package com.noobee.nongky.repository

import com.noobee.nongky.api.CafeService
import com.noobee.nongky.model.GetDataCafeResponse
import com.noobee.nongky.model.GetDataDescCafeResponse
import com.noobee.nongky.util.Resource
import retrofit2.Response
import javax.inject.Inject

class CafeRepository @Inject constructor(
    private val cafeService: CafeService
) {

    suspend fun getDataCafe(): Resource<GetDataCafeResponse> {
        cafeService.getDataCafe().let { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    return Resource.Success(it)
                }
            }

            return Resource.Error(response.message())
        }
    }

    suspend fun getDescCafe(id: String): Resource<GetDataDescCafeResponse> {
        cafeService.getDescCafe(id).let { response ->
            if (response.isSuccessful)
                response.body()?.let { return Resource.Success(it) }

            return Resource.Error(response.message())
        }
    }

//    suspend fun getDataLainnya(): Resource<DataFirebase?> {
//        var isError = false
//        var returnData: DataFirebase? = null
//        var errorMessage = ""
//
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                returnData = snapshot.getValue<DataFirebase>()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                isError = true
//                errorMessage = error.message
//            }
//        }
//
//        cafeFirebase.addValueEventListener(postListener)
//
//        if (!isError) {
//            return Resource.Success(returnData)
//        } else {
//            return Resource.Error(errorMessage)
//        }
//    }

}