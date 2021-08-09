package com.noobee.nongky.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.noobee.nongky.repository.CafeRepository
import com.noobee.nongky.ui.BaseViewModel
import com.noobee.nongky.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val cafeRepository: CafeRepository
) : BaseViewModel() {

    companion object {
        const val DETAIL_ACTION_BACK = "detail_action_back"
        const val DETAIL_ACTION_TIMEOUT = "detail_action_timeout"
    }

    val descCafe = MutableLiveData<String>()

    fun backButtonOnClick() {
        action.value = DETAIL_ACTION_BACK
    }

    fun setDesc(idCafe: String) {
        loadingEnabled.value = true
        viewModelScope.launch {
            when (val response = cafeRepository.getDescCafe(idCafe)) {
                is Resource.Success -> {
                    descCafe.postValue(response.dataResource?.data?.desc)
                }
                is Resource.Error -> {
                    action.postValue(DETAIL_ACTION_TIMEOUT)
                }
            }
            loadingEnabled.postValue(false)
        }
    }
}