package com.bignerdranch.android.maptest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class CardAdapter(private val restaurantList: ArrayList<Restaurants>) : RecyclerView.Adapter<CardAdapter.CardHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_cell, parent, false)
        return CardHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val currentItem = restaurantList[position]
        holder.restaImage.setImageResource(currentItem.restaImage)
        holder.restaName.text = currentItem.restaName
        holder.restaInfo.text = currentItem.restaInfo
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val restaImage : ShapeableImageView = itemView.findViewById(R.id.rest_image)
        val restaName : TextView = itemView.findViewById(R.id.rest_name)
        val restaInfo : TextView = itemView.findViewById(R.id.rest_info)
    }
}