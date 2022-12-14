package models

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class Database {
    private val db = Firebase.firestore
    lateinit var listUsers: List<User>
    var listRestaurants: MutableList<Restaurant> = mutableListOf()

    public fun getUsers() {
        db.collection("users")
            .get()
            .addOnSuccessListener {
                for(result in it) {
                    val user = result.toObject<User>()
                    listUsers = listUsers + user
                }
            }
            .addOnFailureListener {
                Log.w(TAG, "Error getting documents: ", it)
            }
    }

    public fun getRestaurants() {
        listRestaurants.clear()
        db.collection("restaurants")
            .get().addOnSuccessListener{
                for(result in it) {
                    Log.d(TAG, "${result.id} => ${result.data}")
                    val restaurant = result.toObject<Restaurant>()
                    restaurant.id = result.id;
                    Log.d(TAG, "$restaurant && ${restaurant.id}")
                    listRestaurants.add(restaurant)
                }
            }
            .addOnFailureListener {
                Log.w(TAG, "Error getting documents: ", it)
            }
    }

    public fun getRestaurantsByStatus(status: String) {
        listRestaurants.clear()
        db.collection("restaurants")
            .whereEqualTo("status", status)
            .get()
            .addOnSuccessListener{
                for(result in it) {
                    Log.d(TAG, "${result.id} => ${result.data}")
                    val restaurant = result.toObject<Restaurant>()
                    restaurant.id = result.id;
                    Log.d(TAG, "$restaurant && ${restaurant.id}")
                    listRestaurants.add(restaurant)
                }
            }
            .addOnFailureListener {
                Log.w(TAG, "Error getting documents: ", it)
            }
    }
}

data class User(
    val first: String? = null,
    val last: String? = null
)

data class Restaurant(
    var id: String,
    val status: String,
    val lat: Double,
    val long: Double?,
    val name: String
)