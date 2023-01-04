package com.bignerdranch.android.maptest

import android.os.Bundle
import android.os.Looper
import android.view.View
import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.bignerdranch.android.maptest.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.FirebaseApp
import org.robolectric.RobolectricTestRunner
import org.mockito.Mockito
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
// import org.junit.runner.manipulation.Ordering.Context
import org.mockito.Mock

@RunWith(RobolectricTestRunner::class)
class MapsActivityTest {

    @Test
    fun testOnCreate() {
        val activity = MapsActivity()
        val mockBinding = Mockito.mock(ActivityMapsBinding::class.java)
        val mockContext = Mockito.mock(Context::class.java) as android.content.Context
        FirebaseApp.initializeApp(mockContext)
        val mockFragmentManager = Mockito.mock(FragmentManager::class.java)
        val mockMapFragment = Mockito.mock(SupportMapFragment::class.java)
        val mockLocationClient = Mockito.mock(FusedLocationProviderClient::class.java)
        val mockView = Mockito.mock(View::class.java)

        Mockito.`when`(mockFragmentManager.findFragmentById(R.id.map)).thenReturn(mockMapFragment)
        Mockito.`when`(mockBinding.root).thenReturn(mockView as ConstraintLayout?)

        activity.fusedLocationClient = mockLocationClient

        val savedInstanceState: Bundle? = null
        activity.onCreate(savedInstanceState)

        Mockito.verify(activity).setContentView(mockView)
        Mockito.verify(mockMapFragment).getMapAsync(activity)
    }


    @Test
    fun getUser() {
    }

    @Test
    fun onCreate() {
    }

    @Test
    fun onMapReady() {
    }

    @Test
    fun onMarkerClick() {
    }
}