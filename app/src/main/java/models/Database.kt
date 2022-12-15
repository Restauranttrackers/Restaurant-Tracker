package models

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class Database {
    private val db = Firebase.firestore
    var listRestaurants: MutableList<Restaurant> ?= mutableListOf()
    var listUser: MutableList<User> ?= mutableListOf()
    var userID = "NIigcM1NzqtO0omZmZF0"

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

    public fun getUser() {
        db.collection("users").document(userID)
            .get().addOnSuccessListener {
                val activeUser = it.toObject<User>()
                if (activeUser != null) {
                    listUser?.add(activeUser)
                }
                }
            .addOnFailureListener { Log.w(TAG, "Error getting user") }
    }


    public fun getRestaurants() {
        listRestaurants?.clear()
        db.collection("restaurants")
            .get().addOnSuccessListener{
                for(result in it) {
                    //Log.d(TAG, "${result.id} => ${result.data}")
                    var restaurant = result.toObject<Restaurant>()
                    restaurant.id = result.id;
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
                    var restaurant = result.toObject<Restaurant>()
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
    val first: String? = null,
    val last: String? = null,
    var score: Int? = null,
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