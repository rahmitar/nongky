package com.noobee.nongky.ui.list

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
class ListKategoriViewModel @Inject constructor(
    val cafeRepository: CafeRepository
) : BaseViewModel() {

    companion object {
        const val ACTION_LIST_TIMEOUT = "action_list_timeout"
        const val ACTION_LIST_ITEMUPDATE = "action_list_itemupdate"
        const val ACTION_LIST_SEARCH = "action_list_search"
        const val ACTION_LIST_ITEM_CLICK = "action_list_item_click"
    }

    val listKategoriCafe = ArrayList<DataCafe>()
    val temporarylist = ArrayList<DataCafe>()
    val itemPosition = MutableLiveData<Int>()
    val latitudeUser = MutableLiveData<Double>()
    val longitudeUser = MutableLiveData<Double>()

    init {
        latitudeUser.value = 0.0
        longitudeUser.value = 0.0
    }

    fun setList(tags: String?) {
        loadingEnabled.value = true
        viewModelScope.launch {
            when (val response = cafeRepository.getDataCafe()) {
                is Resource.Success -> {
                    listKategoriCafe.clear()
                    response.dataResource?.data?.forEach { item ->
//                        val descResponse = cafeRepository.getDescCafe(item._id)
//
//                        // Get Description
//                        descResponse.dataResource?.data?.let {
//                            item.c_desc = it.desc
//                            item.c_feature = it.features
//                        }

                        item.c_distance = haversine(
                            latitudeUser.value ?: 0.0,
                            longitudeUser.value ?: 0.0,
                            item.c_coordinate
                        )

                        if (item.c_tags.contains(tags)) {
                            listKategoriCafe.add(item)
                            temporarylist.add(item)
                        }

                        listKategoriCafe.sortBy { it.c_distance }
                        temporarylist.sortBy { it.c_distance }
                    }
                    action.postValue(ACTION_LIST_ITEMUPDATE)
                    loadingEnabled.postValue(false)
                }
                is Resource.Error -> {
                    loadingEnabled.postValue(false)
                    action.postValue(ACTION_LIST_TIMEOUT)
                }
            }
        }
    }

    fun listKategoriOnClick(position: Int) {
        itemPosition.value = position
        action.value = ACTION_LIST_ITEM_CLICK
    }

    fun filter(text: String) {
        listKategoriCafe.clear()
        temporarylist.forEach { item ->
            if (item.c_name.contains(text, ignoreCase = true)) {
                listKategoriCafe.add(item)
            }
        }
        action.value = ACTION_LIST_SEARCH
    }
}