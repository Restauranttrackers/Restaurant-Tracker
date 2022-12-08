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
    lateinit var listIDrest: MutableList<String>

    public fun getUsers() {
        db.collection("users")
            .get()
            .addOnSuccessListener {
                for(result in it) {
                    val user = result.toObject<User>()
                    listUsers += user
                }
            }
            .addOnFailureListener {
                Log.w(TAG, "Error getting documents: ", it)
            }
    }

    public fun getRestaurants() {
        Log.d(TAG,"getRestaruants function start")

        db.collection("restaurants")
            .get()
            .addOnSuccessListener {
                for(result in it) {
                    Log.d(TAG, "${result.id} => ${result.data}")
                    val restaurant = result.toObject<Restaurant>()
                    Log.d(TAG, "${restaurant}")
                    listRestaurants.add(restaurant)
                    //listRestaurants += restaurant // crashes app
                }
            }
            .addOnFailureListener {
                Log.w(TAG, "Error getting documents: ", it)
            }
        //Log.d(TAG, "${listRestaurants.elementAt(1)}") // crashes app
        Log.d(TAG,"getRestaruants function end")

    }
}

data class User(
    val first: String? = null,
    val last: String? = null
)

data class Restaurant(
    val id: String? = null,
    val color: String? = null,
    val lat: Int? = null,
    val long: Int? = null,
    val name: String? = null
)