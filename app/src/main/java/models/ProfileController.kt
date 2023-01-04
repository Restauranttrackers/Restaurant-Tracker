package models

import com.bignerdranch.android.maptest.MapsActivity.Companion.data

/**
 * Initializes user from database. Used in profile fragment.
 */
  class ProfileController(private var picture: Int, private var border: Int) {
      private var score: Int = data.listUser[0].score as Int
      private var userName: String = (data.listUser[0].firstname as String) + " " + (data.listUser[0].lastname as String)
      private var title: String = data.listUser[0].banner as String

      public fun getBorder(): Int{
          return this.border
      }
      public fun getPicture(): Int{
          return this.picture
      }
    public fun setPicture(picture: Int){
        this.picture = picture
    }
    public fun setBorder(border: Int){
        this.border = border
    }
      public fun getScore(): Int{
          return this.score
      }
      public fun getUsername(): String{
          return this.userName
      }
      public fun getTitle(): String{
          return this.title
      }

 }
