package com.noobee.nongky.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.noobee.nongky.model.CCoordinate
import com.noobee.nongky.model.Data
import com.noobee.nongky.repository.CafeRepository
import com.noobee.nongky.ui.BaseViewModel
import com.noobee.nongky.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: CafeRepository
) : BaseViewModel() {

    companion object {
        const val ACTION_MAP_TIMEOUT = "action_map_timeout"
        const val ACTION_MAP_LIST_READY = "action_map_list_ready"
    }

    val listTitikCafe = ArrayList<Data>()
    val areListReady = MutableLiveData<Boolean>()

    init {
        areListReady.value = false
    }

    fun getSemuaTitik() {
        loadingEnabled.value = true
        viewModelScope.launch {
            when( val response = repository.getDataCafe()) {
                is Resource.Success -> {
                    when(response.dataResource?.status){
                        HttpURLConnection.HTTP_OK -> {
                            response.dataResource.data?.forEach {
                                listTitikCafe.add(it)
                                areListReady.postValue(true)
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    action.postValue(ACTION_MAP_TIMEOUT)
                }
            }
        }
    }
}