package com.bignerdranch.android.maptest

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.SystemClock.sleep
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.bignerdranch.android.maptest.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import models.Database


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    private val data = Database()

    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //data.getRestaurants() // calls when the app runs so that we can access this in setupMap
        data.getRestaurantsByStatus("Planned") // calls when the app runs so that we can access this in setupMap
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                // move everything that is related to map into map() fragment
                // not getting all map functions atm
                R.id.map -> replaceFragment(map())
                R.id.list -> replaceFragment(list())
                R.id.profile -> replaceFragment(profile())
                else -> {

                }
            }
            true
        }
    }

    override fun onPause() {
        super.onPause()
        data.listRestaurants?.clear()
    }

    override fun onResume() {
        super.onResume()
        data.getRestaurants()
    }


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.setOnMarkerClickListener(this)

        setupMap()
    }

    @SuppressLint("MissingPermission")
    private fun setupMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }

        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong, "My Position $currentLatLong", "test")
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))

            }
        }

        // place initial markers on map from database list
        for (document in data.listRestaurants!!) {
            val rOnePos = LatLng(document.lat as Double, document.long as Double)
            placeMarkerOnMap(rOnePos, document.name as String, document.status as String)
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng, title: String, restaurantStatus: String){
        val greenMarker = BitmapDescriptorFactory.HUE_GREEN
        val redMarker = BitmapDescriptorFactory.HUE_RED
        val yellowMarker = BitmapDescriptorFactory.HUE_YELLOW
        val orangeMarker = BitmapDescriptorFactory.HUE_ORANGE
        val placeholderMarker = BitmapDescriptorFactory.HUE_VIOLET
        val colorMarker = when (restaurantStatus) {
            "Visited" -> greenMarker
            "Not visited" -> redMarker
            "Planned" -> orangeMarker
            "Hidden" -> yellowMarker
            else -> placeholderMarker
        }

        val markerOptions = MarkerOptions().position(currentLatLong).icon(BitmapDescriptorFactory.defaultMarker(colorMarker))
        markerOptions.title(title)
        mMap.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker) = false

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

}
