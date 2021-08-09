package com.noobee.nongky.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.noobee.nongky.model.DataCafe
import com.noobee.nongky.repository.CafeRepository
import com.noobee.nongky.ui.BaseViewModel
import com.noobee.nongky.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val cafeRepository: CafeRepository
) : BaseViewModel() {

    companion object {
        const val ACTION_HOME_TIMEOUT = "action_home_timeout"
        const val ACTION_HOME_ITEMUPDATE = "action_home_itemupdate"
        const val ACTION_HOME_ITEMONCLICK = "action_home_itemonclick"
        const val ACTION_HOME_CAFE = "action_home_cafe"
        const val ACTION_HOME_RESTO = "action_home_resto"
        const val ACTION_HOME_LAINNYA = "action_home_lainnya"
    }

    val listCafe = ArrayList<DataCafe>()
    val actionItemPosition = MutableLiveData<Int>()
    val latitudeUser = MutableLiveData<Double>()
    val longitudeUser = MutableLiveData<Double>()
    val isNearOneKilometers = MutableLiveData<Boolean>()

    init {
        latitudeUser.value = 0.0
        longitudeUser.value = 0.0
        isNearOneKilometers.value = false
    }

    fun setList() {
        loadingEnabled.value = true
        viewModelScope.launch {
            when (val response = cafeRepository.getDataCafe()) {
                is Resource.Success -> {
                    listCafe.clear()

                    var countListCafe = 0
                    response.dataResource?.data?.forEach { item ->
                        item.c_distance = haversine(
                            latitudeUser.value ?: 0.0,
                            longitudeUser.value ?: 0.0,
                            item.c_coordinate
                        )

                        item.c_distance?.let {
                            if (it <= 1.00) {
                                listCafe.add(item)
                                countListCafe++
                            }
                        }
                    }
                    listCafe.sortBy { it.c_distance }
                    listCafe.take(10)

                    action.postValue(ACTION_HOME_ITEMUPDATE)
                    loadingEnabled.postValue(false)

                    if (countListCafe > 0) {
                        isNearOneKilometers.postValue(true)
                    }
                }
                is Resource.Error -> {
                    loadingEnabled.postValue(false)
                    action.postValue(ACTION_HOME_TIMEOUT)
                }
            }
        }
    }

    fun itemCafeOnClick(position: Int) {
        actionItemPosition.value = position
        action.value = ACTION_HOME_ITEMONCLICK
    }

    fun buttonCafeOnClick() {
        action.value = ACTION_HOME_CAFE
    }

    fun buttonRestoOnClick() {
        action.value = ACTION_HOME_RESTO
    }

    fun buttonLainnyaOnClick() {
        action.value = ACTION_HOME_LAINNYA
    }

}