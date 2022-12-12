package com.bignerdranch.android.maptest

data class Restaurants(
    var restaImage : Int,
    var restaName : String,
    var restaInfo : String,
    var restaDescr : String,
    var expanded : Boolean = false
)
