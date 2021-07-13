package com.noobee.nongky.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noobee.nongky.model.CCoordinate
import java.lang.Math.*

open class BaseViewModel : ViewModel() {
    val action = MutableLiveData<String>()
    val loadingEnabled = MutableLiveData<Boolean>()

    companion object {
        const val R = 6371 // in kilometers
    }

    fun haversine(lat1: Double, lon1: Double, coordinate:CCoordinate): Double {
        val λ1 = toRadians(lat1)
        val λ2 = toRadians(coordinate.lat)
        val Δλ = toRadians(coordinate.lat - lat1)
        val Δφ = toRadians(coordinate.lon - lon1)

        val d:Double = 2 * R * asin(sqrt(pow(sin(Δλ / 2), 2.0) + pow(sin(Δφ / 2), 2.0) * cos(λ1) * cos(λ2)))
        return round(d * 100.0) / 100.0
    }

}