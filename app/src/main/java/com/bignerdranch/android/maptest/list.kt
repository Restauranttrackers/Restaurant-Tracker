package com.bignerdranch.android.maptest

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
    private lateinit var restaurantsArrayList: ArrayList<Restaurants>
    lateinit var imageId : Array<Int>
    lateinit var restaName : Array<String>
    lateinit var restaInfo : Array<String>
    lateinit var restaDescr : Array<String>
    lateinit var restaurants: Array<String>

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
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = CardAdapter(restaurantsArrayList)
        recyclerView.adapter = adapter

    }

    private fun dataInitialize() {
        restaurantsArrayList = arrayListOf<Restaurants>()

        imageId = arrayOf(
            R.drawable.example_restaurant,
            R.drawable.example_restaurant,
            R.drawable.example_restaurant,
            R.drawable.example_restaurant,
            R.drawable.example_restaurant,
            R.drawable.example_restaurant,
            R.drawable.example_restaurant
        )

        restaName = arrayOf(
            "Sumo Kitchen",
            "Smoke",
            "Royal Thai",
            "Max",
            "Montmartre",
            "Pantarholmens Pizzeria",
            "Kebab House"
        )

        restaInfo = arrayOf(
            "Asiatisk mat",
            "Dålig mat :(",
            "Asiatisk mat",
            "Snabbmat",
            "Italiensk mat",
            "Karlskronas godaste pizza",
            "Kebab"
        )

        restaDescr = arrayOf(
            getString(R.string.Lorem_ipsum),
            getString(R.string.Lorem_ipsum),
            getString(R.string.Lorem_ipsum),
            getString(R.string.Lorem_ipsum),
            getString(R.string.Lorem_ipsum),
            getString(R.string.Lorem_ipsum),
            getString(R.string.Lorem_ipsum)
        )

        restaurants = arrayOf(
            "?",
            "?",
            "?",
            "?",
            "?",
            "?",
            "?"
        )

        for (i in imageId.indices) {
            val restaurants = Restaurants(imageId[i], restaName[i], restaInfo[i], restaDescr[i])
            restaurantsArrayList.add(restaurants)
        }
    }
}