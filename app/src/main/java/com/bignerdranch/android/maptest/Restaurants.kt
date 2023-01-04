package com.bignerdranch.android.maptest

data class Restaurants(
    var restaImage: Int, // Restaurant image
    var restaName: String, // Restaurant name
    var restaInfo: String, // Some info like what type of food
    var restaDescr: String, // Restaurant description
    var mark: String, // Current set mark of restaurant
    var expanded : Boolean = false, // If card is expanded or not
    var filtered : Boolean = true
)
