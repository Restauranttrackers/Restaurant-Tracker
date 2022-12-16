package models

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import com.bignerdranch.android.maptest.MapsActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.Thread.sleep

class Database {
    private val db = Firebase.firestore
    var listRestaurants: MutableList<Restaurant> ?= mutableListOf()
    var listUser: MutableList<User> = mutableListOf<User>()
    private var userID = "NIigcM1NzqtO0omZmZF0"

    public fun userScoreIncrease(scoreIncrease: Int) {
        var newScore = 0
        db.collection("users").document(userID)
            .get().addOnSuccessListener {
                val activeUser = it.toObject<User>()
                Log.d(TAG, "${it.id} => ${it.data}")
                if (activeUser != null) {
                    newScore += activeUser.score?.plus(scoreIncrease) ?: 0
                    val userToUpdate = db.collection("users").document(userID)
                    userToUpdate
                        .update("score", newScore)
                        .addOnSuccessListener { Log.w(TAG, "Successfully updated user score") }
                        .addOnFailureListener { Log.w(TAG, "Error updating user score") }
                }
            }
            .addOnFailureListener { Log.w(TAG, "Error getting user") }
    }

    @SuppressLint("SuspiciousIndentation")
    public fun getUser() {
        db.collection("users").document(userID)
            .get().addOnSuccessListener {
                val activeUser = it.toObject<User>()
                    listUser.add(activeUser!!)
                    //Log.d("database user", "listUser $listUser ")
                }
            .addOnFailureListener {
                Log.w(TAG, "Error getting user")
            }
    }


    fun getRestaurants() {
        listRestaurants?.clear()
        db.collection("restaurants")
            .get().addOnSuccessListener{
                for(result in it) {
                    //Log.d(TAG, "${result.id} => ${result.data}")
                    val restaurant = result.toObject<Restaurant>()
                    restaurant.id = result.id
                    //Log.d(TAG, "${restaurant} && ${restaurant.id}")
                    listRestaurants?.add(restaurant)
                }
            }
            .addOnFailureListener {
                Log.w(TAG, "Error getting documents: ", it)
            }
    }

    public fun getRestaurantsByStatus(status: String) {
        listRestaurants?.clear()
        db.collection("restaurants")
            .whereEqualTo("status", status)
            .get().addOnSuccessListener{
                for(result in it) {
                    //Log.d(TAG, "${result.id} => ${result.data}")
                    val restaurant = result.toObject<Restaurant>()
                    restaurant.id = result.id;
                    //Log.d(TAG, "${restaurant} && ${restaurant.id}")
                    listRestaurants?.add(restaurant)
                }
            }
            .addOnFailureListener {
                Log.w(TAG, "Error getting documents: ", it)
            }
    }

    public fun updateRestaurantStatus(newStatus: String, restaurantId: String) {
        val restaurantToChange = db.collection("restaurants").document(restaurantId)
        restaurantToChange
            .update("status", newStatus)
            .addOnSuccessListener { Log.w(TAG, "Successfully updated document status") }
            .addOnFailureListener { Log.w(TAG, "Error updating document status") }
    }
}

data class User(
    val firstname: String? = null,
    val lastname: String? = null,
    val score: Int? = null,
    val banner: String? = null
)

data class Restaurant(
    var id: String? = null,
    val status: String? = null,
    val lat: Double? = null,
    val long: Double? = null,
    val name: String? = null,
    val image: String? = null,
    val info: String? = null,
    val description: String? = null
)