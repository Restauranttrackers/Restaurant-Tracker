package com.bignerdranch.android.maptest

import android.annotation.SuppressLint
import android.content.ContentValues
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.maptest.MapsActivity.Companion.data

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [list.newInstance] factory method to
 * create an instance of this fragment.
 */
class list : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: CardAdapter
    private lateinit var recyclerView: RecyclerView
    private var restaurantsArrayList: MutableList<Restaurants> = mutableListOf()

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
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment list.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            list().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRestaurantData()
        setRecyclerView(view)
    }

    private fun setRecyclerView(view: View) {
        // Setting recyclerView to a LinearLayout
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        // Initializing CardAdapter class with restaurants
        adapter = CardAdapter(restaurantsArrayList, data)
        // Setting adapter to recyclerView
        recyclerView.adapter = adapter
    }

    private fun getRestaurantData() {
        // Get restaurant data from database
        for(document in data.flexibleRestaurantList!!) {
            initRestaurantArray(document.id as String, document.name as String, document.status as String, document.info as String, document.description as String, document.image as String)
        }
    }

    private fun initRestaurantArray(id: String, name: String, status: String, info: String, description: String, image_url: String) {
        val imageId: String = image_url
        val restaId: String = id
        val restaName : String = name
        val restaInfo : String = info
        val restaDescr : String = description
        val mark: String = status

        val restaurants = Restaurants(restaId, imageId, restaName, restaInfo, restaDescr, mark)
        restaurantsArrayList.add(restaurants)
    }
}

data class Restaurants(
    var restaId: String, // Restaurant ID
    var restaImage: String, // Restaurant image
    var restaName: String, // Restaurant name
    var restaInfo: String, // Some info like what type of food
    var restaDescr: String, // Restaurant description
    var mark: String, // Current mark of restaurant
    var expanded : Boolean = false // If card is expanded or not
)
