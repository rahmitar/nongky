package com.noobee.nongky.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.noobee.nongky.R
import com.noobee.nongky.databinding.FragmentHomeBinding
import com.noobee.nongky.ui.detail.DetailActivity
import com.noobee.nongky.ui.list.ListKategoriActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeFragmentBinding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var listDetailAdapter: RecyclerViewListDetailTempatMakanAdapter
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listDetailAdapter = RecyclerViewListDetailTempatMakanAdapter(homeViewModel)
        homeFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return homeFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        homeFragmentBinding.apply {
            lifecycleOwner = this@HomeFragment
            viewModel = homeViewModel
        }

        homeViewModel.action.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                HomeViewModel.ACTION_HOME_ITEMUPDATE -> listItemUpdate()
                HomeViewModel.ACTION_HOME_ITEMONCLICK -> cafeItemOnClick()
                HomeViewModel.ACTION_HOME_TIMEOUT -> connectionTimeOut()
                HomeViewModel.ACTION_HOME_CAFE -> buttonCafeOnClick()
                HomeViewModel.ACTION_HOME_RESTO -> buttonRestoOnClick()
                HomeViewModel.ACTION_HOME_LAINNYA -> buttonLainnyaOnClick()

            }
        })
        getLocationPermission()
        homeViewModel.setList()
    }

    override fun onResume() {
        super.onResume()
        getLocationPermission()
    }

    private fun buttonLainnyaOnClick() {
        val intent = Intent(requireContext(), ListKategoriActivity::class.java)
        intent.putExtra(ListKategoriActivity.EXTRA_KATEGORI, "lainnya")

        startActivity(intent)
    }

    private fun buttonRestoOnClick() {
        val intent = Intent(requireContext(), ListKategoriActivity::class.java)
        intent.putExtra(ListKategoriActivity.EXTRA_KATEGORI, "resto")

        startActivity(intent)
    }

    private fun buttonCafeOnClick() {
        val intent = Intent(requireContext(), ListKategoriActivity::class.java)
        intent.putExtra(ListKategoriActivity.EXTRA_KATEGORI, "cafe")

        startActivity(intent)
    }

    private fun cafeItemOnClick() {
        try {

            val positionItem = homeViewModel.actionItemPosition.value
            val dataClick = positionItem?.let { homeViewModel.listCafe[it] }

            val intent = Intent(requireActivity(), DetailActivity::class.java)

            if (dataClick?.c_desc.isNullOrBlank() || dataClick?.c_feature.isNullOrEmpty()) {
                dataClick?.c_desc = ""
                dataClick?.c_feature = listOf()
            }

            intent.putExtra(DetailActivity.EXTRA_PARCEL_DETAIL, dataClick)
            startActivity(intent)

        } catch (e: NullPointerException) {
            Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun connectionTimeOut() {
        Toast.makeText(requireContext(), "Koneksi Gagal", Toast.LENGTH_SHORT).show()
    }

    private fun listItemUpdate() {
        listDetailAdapter.listItem = homeViewModel.listCafe
        listDetailAdapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        homeFragmentBinding.recyclerViewListHome.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = listDetailAdapter
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())
        }
    }

    private fun getLocationPermission() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {
            it?.let {
                homeViewModel.latitudeUser.value = it.latitude
                homeViewModel.longitudeUser.value = it.longitude
//                Toast.makeText(requireContext(), "lat user : ${it.latitude}", Toast.LENGTH_SHORT).show()
            }
        }
    }


}