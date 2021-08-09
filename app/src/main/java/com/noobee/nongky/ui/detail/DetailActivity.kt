package com.noobee.nongky.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.noobee.nongky.R
import com.noobee.nongky.databinding.ActivityDetailBinding
import com.noobee.nongky.model.DataCafe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()

    companion object {
        const val EXTRA_PARCEL_DETAIL = "extra_parcel_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val dataParcel = intent.getParcelableExtra<DataCafe>(EXTRA_PARCEL_DETAIL)

        detailBinding.apply {
            lifecycleOwner = this@DetailActivity
            viewModel = detailViewModel
            data = dataParcel
        }

        detailViewModel.action.observe(this, { action ->
            when (action) {
                DetailViewModel.DETAIL_ACTION_BACK -> backButtonOnClick()
            }
        })

        detailViewModel.setDesc(dataParcel?._id ?: "")
    }

    private fun backButtonOnClick() {
        finish()
    }
}