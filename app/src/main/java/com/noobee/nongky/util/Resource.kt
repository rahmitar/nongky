package com.noobee.nongky.util

sealed class Resource<T>(
    val dataResource: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data,message)
}
