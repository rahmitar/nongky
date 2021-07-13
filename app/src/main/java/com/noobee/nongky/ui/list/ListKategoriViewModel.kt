package com.noobee.nongky.ui.list

import androidx.lifecycle.viewModelScope
import com.noobee.nongky.model.Data
import com.noobee.nongky.repository.CafeRepository
import com.noobee.nongky.ui.BaseViewModel
import com.noobee.nongky.ui.home.HomeViewModel
import com.noobee.nongky.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListKategoriViewModel @Inject constructor(
    val cafeRepository: CafeRepository
) : BaseViewModel() {

    companion object{
        const val ACTION_LIST_TIMEOUT = "action_list_timeout"
        const val ACTION_LIST_ITEMUPDATE = "action_list_itemupdate"
    }

    val listKategoriCafe = ArrayList<Data>()

    fun setList(tags: String?){
        viewModelScope.launch {
            when (val response = cafeRepository.getDataCafe()) {
                is Resource.Success -> {
                    loadingEnabled.postValue(false)

                    listKategoriCafe.clear()
                    response.dataResource?.data?.forEach { item ->
                        if(item.c_tags.contains(tags)){
                            listKategoriCafe.add(item)
                        }
                    }
                    action.postValue(ACTION_LIST_ITEMUPDATE)
                }
                is Resource.Error -> {
                    loadingEnabled.postValue(false)
                    action.postValue(ACTION_LIST_TIMEOUT)
                }
            }
        }
    }

}