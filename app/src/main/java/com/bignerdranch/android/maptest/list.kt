package com.bignerdranch.android.maptest

import android.annotation.SuppressLint
import android.content.ContentValues
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.maptest.MapsActivity.Companion.data
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import models.Database
import models.Restaurant

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
    /*lateinit var imageId : Array<Int>
    lateinit var restaName : Array<String>
    lateinit var restaInfo : Array<String>
    lateinit var restaDescr : Array<String>
    private lateinit var restaurants: Array<String>*/

    // Database
    //val db = Firebase.firestore
    //private val data = Database()

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

        //data.getRestaurantsByStatus("Visited")
        //getData()

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = CardAdapter(restaurantsArrayList)
        recyclerView.adapter = adapter
    }

    private fun getData() {
        for (i in 1..4) {
            dataInitialize("Restaurant $i", "Not Visited")
        }
        for (i in 5..13) {
            dataInitialize("Restaurant $i", "Visited")
        }
        for (i in 14..20) {
            dataInitialize("Restaurant $i", "Planned")
        }
        for (i in 21..23) {
            dataInitialize("Restaurant $i", "Hidden")
        }

        /*db.collection("restaurants")
            .get()
            .addOnSuccessListener { result ->
                for(document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    Log.d(ContentValues.TAG, "${document.data["name"]}")
                    // restaurant test
                    dataInitialize(restaurantsArrayList, document.data["name"] as String, document.data["status"] as String)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "error getting documents.", exception)
            }*/
    }

    private fun dataInitialize(title: String, restaurantStatus: String) {
        val imageId : Int = R.drawable.example_restaurant
        val restaName : String = title
        val restaInfo : String = getString(R.string.some_info_like_category_of_restaurant)
        val restaDescr : String = getString(R.string.Lorem_ipsum)
        val mark: String = restaurantStatus

        val restaurants = Restaurants(imageId, restaName, restaInfo, restaDescr, mark)
        restaurantsArrayList.add(restaurants)
    }
}