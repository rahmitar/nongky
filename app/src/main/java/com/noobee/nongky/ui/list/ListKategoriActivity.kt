package com.noobee.nongky.ui.list

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.noobee.nongky.R
import com.noobee.nongky.databinding.ActivityListKategoriBinding
import com.noobee.nongky.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListKategoriActivity : AppCompatActivity() {

    private lateinit var listKategoriBinding: ActivityListKategoriBinding
    private val listKategoriViewModel: ListKategoriViewModel by viewModels()
    private lateinit var listKategoriAdapter: RecylerViewListKategoriAdapter
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        const val EXTRA_KATEGORI = "extra_kategori"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listKategoriBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_kategori)
        listKategoriAdapter = RecylerViewListKategoriAdapter(listKategoriViewModel)

        setupRecyclerView()
        listKategoriBinding.apply {
            lifecycleOwner = this@ListKategoriActivity
            viewModel = listKategoriViewModel
        }

        listKategoriViewModel.action.observe(this, Observer { action ->
            when (action) {
                ListKategoriViewModel.ACTION_LIST_ITEMUPDATE -> listItemUpdate()
                ListKategoriViewModel.ACTION_LIST_TIMEOUT -> connectionTimeout()
                ListKategoriViewModel.ACTION_LIST_SEARCH -> filterSearch()
                ListKategoriViewModel.ACTION_LIST_ITEM_CLICK -> listItemClick()
            }
        })


        listKategoriBinding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    listKategoriViewModel.filter(it.toString())
                }
            }
        })

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getLocationPermission()

        val tag = intent.getStringExtra(EXTRA_KATEGORI)
        listKategoriViewModel.setList(tag)
    }

    private fun listItemClick() {
        try{
            val positionItem = listKategoriViewModel.itemPosition.value
            val dataClick = positionItem?.let { listKategoriViewModel.listKategoriCafe[it] }

            val intent = Intent(this, DetailActivity::class.java)

            if (dataClick?.c_desc.isNullOrBlank() || dataClick?.c_feature.isNullOrEmpty()) {
                dataClick?.c_desc = ""
                dataClick?.c_feature = listOf()
            }

            intent.putExtra(DetailActivity.EXTRA_PARCEL_DETAIL, dataClick)
            startActivity(intent)
        } catch (e: NullPointerException){
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show()
        }

    }

    private fun connectionTimeout() {
        Toast.makeText(this, "Koneksi Gagal", Toast.LENGTH_SHORT).show()
    }

    private fun listItemUpdate() {
        listKategoriAdapter.listItem = listKategoriViewModel.listKategoriCafe
        listKategoriAdapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        listKategoriBinding.recyclerViewListKategori.apply {
            layoutManager =
                LinearLayoutManager(this@ListKategoriActivity, LinearLayoutManager.VERTICAL, false)
            adapter = listKategoriAdapter
        }
    }

    private fun filterSearch() {
        listKategoriAdapter.notifyDataSetChanged()
    }

    private fun getLocationPermission() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {
            it?.let {
                val user = LatLng(it.latitude, it.longitude)
                listKategoriViewModel.latitudeUser.value = it.latitude
                listKategoriViewModel.longitudeUser.value = it.longitude
            }
        }
    }

}