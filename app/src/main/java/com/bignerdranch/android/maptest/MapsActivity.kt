@file:Suppress("DEPRECATION")

package com.bignerdranch.android.maptest

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.bignerdranch.android.maptest.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import models.Database
import java.lang.Thread.sleep
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityMapsBinding

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    private var fragmentCheck: Boolean = false

    companion object{
        private const val LOCATION_REQUEST_CODE = 1
        val data = Database()
        lateinit var mMap: GoogleMap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data.getRestaurants() // calls when the app runs so that we can access this in setupMap
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.map -> {
                    placeAllMarkerOnMap()
                    hideCurrentFragment()
                    fragmentCheck = false
                    showFilterBar(true)
                }
                R.id.list -> {
                    sleep(200)
                    replaceFragment(list())
                    fragmentCheck = true
                    showFilterBar(true)
                }
                R.id.profile -> {
                    replaceFragment(profile())
                    fragmentCheck = true
                    showFilterBar(false)
                }
                else -> {

                }
            }
            true
        }
        filterByClick()
        data.getUser()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.setOnMarkerClickListener {
                marker ->
                val currentLatLong = LatLng(marker.position.latitude, marker.position.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 14f))
            var id = ""
            for (document in data.flexibleRestaurantList!!) {
                val currentMarkPos = LatLng(document.lat as Double, document.long as Double)
                if (currentMarkPos == currentLatLong) {
                  id = document.id as String
                }
            }
            showInfoCard(id)
        }
        mMap.setOnMapClickListener {
            hideCurrentFragment()
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            sleep(500)
            setupMap()
            sleep(500)
            data.getAllRestaurants()
            sleep(500)
            placeAllMarkerOnMap()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_REQUEST_CODE && PackageManager.PERMISSION_GRANTED in grantResults) {
            sleep(500)
            setupMap()
            sleep(500)
            data.getAllRestaurants()
            sleep(500)
            placeAllMarkerOnMap()
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupMap() {
        mMap.isMyLocationEnabled = true

        mMap.setMapStyle(
            MapStyleOptions(
                "[\n" +
                        " {\n" +
                        "   \"featureType\": \"poi\",\n" +
                        "   \"elementType\": \"all\",\n" +
                        "   \"stylers\": [\n" +
                        "     {\n" +
                        "       \"visibility\": \"off\"\n" +
                        "     }\n" +
                        "   ]\n" +
                        " }\n" +
                        "]",
            )
        )

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))
            }
        }
    }

    fun placeAllMarkerOnMap() {
        mMap.clear()
        for (document in data.flexibleRestaurantList!!) {
            val rOnePos = LatLng(document.lat as Double, document.long as Double)
            placeMarkerOnMap(rOnePos, document.name as String, document.status as String, document.id as String)
        }
    }

    fun placeMarkerOnMap(currentLatLong: LatLng, title: String, restaurantStatus: String, id: String){
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

        val markerOptions = mMap.addMarker(
            MarkerOptions()
                .position(currentLatLong)
                .icon(BitmapDescriptorFactory
                    .defaultMarker(colorMarker))
                .title(title))
        markerOptions?.hideInfoWindow()
    }

    override fun onMarkerClick(p0: Marker) = false

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment, "currentFragment")
        fragmentTransaction.commit()
    }

    private fun hideCurrentFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val currentFrag = fragmentManager.findFragmentByTag("currentFragment")
        if (currentFrag != null) {
            fragmentTransaction.hide(currentFrag)
            fragmentTransaction.commit()
        }
    }

    private fun filterByClick() {
        val notVisited: Button = findViewById(R.id.not_visited_button)
        val visited = findViewById<Button>(R.id.visited_button)
        val planned = findViewById<Button>(R.id.planned_button)
        val hidden = findViewById<Button>(R.id.hidden_button)

        var notVisitedClicked = false
        var visitedClicked = false
        var plannedClicked = false
        var hiddenClicked = false

        notVisited.setOnClickListener {
            if(!notVisitedClicked) {
                data.getRestaurantsByStatus("Not visited")
                notVisited.setBackgroundColor(getColor(R.color.green))
                visited.setBackgroundColor(getColor(R.color.lightGray))
                planned.setBackgroundColor(getColor(R.color.lightGray))
                hidden.setBackgroundColor(getColor(R.color.lightGray))
                notVisitedClicked = true
                visitedClicked = false
                plannedClicked = false
                hiddenClicked = false
            }
            else {
                data.getAllRestaurants()
                notVisited.setBackgroundColor(getColor(R.color.lightGray))
                notVisitedClicked = false
            }
            sleep(200)
            if (fragmentCheck) {
                replaceFragment(list())
            } else {
                placeAllMarkerOnMap()
            }
        }
        visited.setOnClickListener {
            if(!visitedClicked) {
                data.getRestaurantsByStatus("Visited")

                visited.setBackgroundColor(getColor(R.color.green))
                notVisited.setBackgroundColor(getColor(R.color.lightGray))
                planned.setBackgroundColor(getColor(R.color.lightGray))
                hidden.setBackgroundColor(getColor(R.color.lightGray))
                visitedClicked = true
                notVisitedClicked = false
                plannedClicked = false
                hiddenClicked = false
            }
            else {
                data.getAllRestaurants()
                visited.setBackgroundColor(getColor(R.color.lightGray))
                visitedClicked = false
            }
            sleep(200)

            if (fragmentCheck) {
                replaceFragment(list())
            } else {
                placeAllMarkerOnMap()
            }
        }
        planned.setOnClickListener{
            if(!plannedClicked) {
                data.getRestaurantsByStatus("Planned")
                planned.setBackgroundColor(getColor(R.color.green))
                notVisited.setBackgroundColor(getColor(R.color.lightGray))
                visited.setBackgroundColor(getColor(R.color.lightGray))
                hidden.setBackgroundColor(getColor(R.color.lightGray))
                plannedClicked = true
                notVisitedClicked = false
                visitedClicked = false
                hiddenClicked = false
            }
            else {
                data.getAllRestaurants()
                planned.setBackgroundColor(getColor(R.color.lightGray))
                plannedClicked = false
            }
            sleep(200)
            if (fragmentCheck) {
                replaceFragment(list())
            } else {
                placeAllMarkerOnMap()
            }
        }
        hidden.setOnClickListener {
            if(!hiddenClicked) {
                data.getRestaurantsByStatus("Hidden")
                hidden.setBackgroundColor(getColor(R.color.green))
                notVisited.setBackgroundColor(getColor(R.color.lightGray))
                visited.setBackgroundColor(getColor(R.color.lightGray))
                planned.setBackgroundColor(getColor(R.color.lightGray))
                hiddenClicked = true
                notVisitedClicked = false
                visitedClicked = false
                plannedClicked = false
            }
            else {
                data.getAllRestaurants()
                hidden.setBackgroundColor(getColor(R.color.lightGray))
                hiddenClicked = false
            }
            sleep(200)
            if (fragmentCheck) {
                replaceFragment(list())
            } else {
                placeAllMarkerOnMap()
            }
        }
    }

    private fun showFilterBar(filterVisible: Boolean) {
        val filter: ConstraintLayout = findViewById(R.id.filter_bar)
        if(!filterVisible) {
            filter.visibility = View.GONE
        }
        if(filterVisible) {
            filter.visibility = View.VISIBLE
        }

    }

    private fun showInfoCard(id: String): Boolean {
        replaceFragment(map(id))
        return true
    }

}