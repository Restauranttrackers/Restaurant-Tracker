package com.bignerdranch.android.maptest

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
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
import models.Database


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var lastLocation: Location
    lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }


    // Database test
    val db = Firebase.firestore
    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace"
    )
    // add user
    /*
         db.collection("users").add(user).addOnSuccessListener { documentReference ->
            Log.d(TAG, "documentsnapshot added with ID: ${documentReference.id}")
        }.addOnFailureListener { e ->
            Log.w(TAG, "error adding document", e)
        }*/



    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                R.id.map -> replaceFragment(mapFragment)
                R.id.list -> replaceFragment(list())
                R.id.profile -> replaceFragment(profile())
                else -> {

                }
            }
            true
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        // can i update this->restaurant from here ? (database) or should it be done from func above?
        mMap.setOnMarkerClickListener(this)

        setupMap()
        Log.d(TAG, "test######")

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
        // read from database test
        db.collection("restaurants")
            .get()
            .addOnSuccessListener { result ->

                for(document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    Log.d(TAG, "${document.data["name"]}")
                    // restaurant test
                    val rOnePos = LatLng(document.data["lat"] as Double, document.data["long"] as Double)
                    placeMarkerOnMap(rOnePos, document.data["name"] as String, document.data["status"] as String)

                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "error getting documents.", exception)
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
            "Planned" -> yellowMarker
            "Hidden" -> orangeMarker
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
