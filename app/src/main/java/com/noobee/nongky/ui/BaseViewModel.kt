package com.noobee.nongky.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Math.*

open class BaseViewModel : ViewModel() {
    val action = MutableLiveData<String>()
    val loadingEnabled = MutableLiveData<Boolean>()

    fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val λ1 = toRadians(lat1)
        val λ2 = toRadians(lat2)
        val Δλ = toRadians(lat2 - lat1)
        val Δφ = toRadians(lon2 - lon1)

        val d:Double = 2 * Companion.R * asin(sqrt(pow(sin(Δλ / 2), 2.0) + pow(sin(Δφ / 2), 2.0) * cos(λ1) * cos(λ2)))
        return Math.round(d * 100.0) / 100.0
    }

    companion object {
        const val R = 6371 // in kilometers
    }
}