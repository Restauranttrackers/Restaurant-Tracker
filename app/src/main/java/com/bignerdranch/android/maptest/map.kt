package com.bignerdranch.android.maptest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bignerdranch.android.maptest.MapsActivity.Companion.data
import com.bignerdranch.android.maptest.MapsActivity.Companion.mMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [map.newInstance] factory method to
 * create an instance of this fragment.
 */
class map(id: String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var restaurantsArrayList: MutableList<Restaurants> = mutableListOf()
    private val restaurantID: String = id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment map.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            map("").apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRestaurantData()
        dataToCard(view)
    }

    private fun getRestaurantData() {
        for(document in MapsActivity.data.flexibleRestaurantList!!) {
            if(document.id == restaurantID) {
                dataInitialize(document.id as String, document.name as String, document.status as String, document.info as String, document.description as String, document.image as String)
            }
        }
    }

    private fun dataInitialize(id: String, name: String, status: String, info: String, description: String, image_url: String) {
        val restaId: String = id
        val imageId: String = image_url
        val restaName : String = name
        val restaInfo : String = info
        val restaDescr : String = description
        val mark: String = status

        val restaurants = Restaurants(restaId, imageId, restaName, restaInfo, restaDescr, mark)
        restaurantsArrayList.add(restaurants)
    }

    private fun dataToCard(view: View) {
        val restaImage: ShapeableImageView = view.findViewById(R.id.rest_image)
        val restaName: TextView = view.findViewById(R.id.rest_name)
        val restaInfo: TextView = view.findViewById(R.id.rest_info)
        val restaDescr: TextView = view.findViewById(R.id.rest_desc)

        Picasso.get().load(restaurantsArrayList[0].restaImage).placeholder(R.drawable.example_image).into(restaImage)
        restaName.text = restaurantsArrayList[0].restaName
        restaInfo.text = restaurantsArrayList[0].restaInfo
        restaDescr.text = restaurantsArrayList[0].restaDescr

        // Dropdown
        val autoTextView = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val marks = view.resources.getStringArray(R.array.marks)
        val arrayAdapter = ArrayAdapter(view.context, R.layout.dropdown_item, marks)
        autoTextView.setAdapter(arrayAdapter)
        val input: TextInputLayout = view.findViewById(R.id.dropdown_menu)
        val submitButton: Button = view.findViewById(R.id.submit_button)

        // Set mark
        submitButton.setOnClickListener {
            val empty = ""
            var chosenMark: String = input.editText?.text.toString()
            if (chosenMark != empty) {
                val currMark = restaurantsArrayList[0].mark
                val restaId = restaurantsArrayList[0].restaId
                if (chosenMark != currMark) {
                    data.updateRestaurantStatus(chosenMark, restaId)
                    // Refresha MapsActivity på något sätt så att databaslistan blir uppdaterad
                    // Något i stil med hideCurrentFragment här kanske beroende på hur det blir med Refresh :)
                    Toast.makeText(view.context, "Restaurant status set to: ${chosenMark}", Toast.LENGTH_SHORT).show()
                    for (document in data.flexibleRestaurantList!!) {
                        if (document.id == restaId) {
                            document.status = chosenMark
                        }
                        placeAllMarkerOnMap()
                    }
                }
            }
        }

        // Remove later
        val restaMark: TextView = view.findViewById(R.id.rest_mark)
        restaMark.text = restaurantsArrayList[0].mark
    }

    private fun placeAllMarkerOnMap() {
        // Same as function in MapsActivity, but doesn't work to import it
        mMap.clear()
        for (document in data.flexibleRestaurantList!!) {
            val rOnePos = LatLng(document.lat as Double, document.long as Double)
            placeMarkerOnMap(rOnePos, document.name as String, document.status as String, document.id as String)
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng, title: String, restaurantStatus: String, id: String){
        // Same as function in MapsActivity, but doesn't work to import it
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
                .icon(
                    BitmapDescriptorFactory
                    .defaultMarker(colorMarker))
                .title(title))
        markerOptions?.hideInfoWindow()
    }
}