package com.noobee.nongky.ui.maps

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.noobee.nongky.R
import com.noobee.nongky.databinding.FragmentMapsBinding
import com.noobee.nongky.util.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment() {

    private lateinit var mapsFragmentBinding: FragmentMapsBinding

    private val mapViewModel: MapViewModel by viewModels()

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var googleMap: GoogleMap

    private val listCafes = ArrayList<Marker>()

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        this.googleMap = googleMap

        googleMap.setOnMapLoadedCallback {
            mapViewModel.apply {
                if (areListReady.value == true) {
                    listTitikCafe.forEach { item ->
                        val color = BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE
                        )
                        val marker =googleMap.addMarker(
                            MarkerOptions()
                                .position(LatLng(item.c_coordinate.lat, item.c_coordinate.lon))
                                .title(item.c_name)
                                .icon(color)
                        )
                        this@MapsFragment.listCafes.add(marker)
                    }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())

        getLocationPermission()
        return mapsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapsFragmentBinding.apply {
            lifecycleOwner = this@MapsFragment
            mapFragment?.getMapAsync(callback)
        }

        mapViewModel.action.observe(viewLifecycleOwner, { action ->
            when(action){
                MapViewModel.ACTION_MAP_LIST_READY -> onListReady()
            }
        })
        mapViewModel.getSemuaTitik()
    }

    private fun onListReady(){
        callback.onMapReady(googleMap)
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
                val user = LatLng(it.latitude, it.longitude)
                googleMap.addMarker(MarkerOptions().position(user).title("Marker in Sydney"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 13.5f))
                //Toast.makeText(this.requireContext(), "user lat: ${it.latitude} long: ${it.longitude} "  , Toast.LENGTH_LONG).show()
                mapViewModel.latitudeUser.value = it.latitude
                mapViewModel.longitudeUser.value = it.longitude

                googleMap.addCircle(
                    CircleOptions()
                        .center(LatLng(it.latitude, it.longitude))
                        .radius(2000.0)
                        .strokeWidth(5f)
                        .strokeColor(Color.BLUE)
                        .fillColor(Color.argb(128, 0, 0, 255))
                )
            }
        }
    }

}