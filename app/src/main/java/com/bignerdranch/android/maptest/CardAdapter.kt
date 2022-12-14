package com.bignerdranch.android.maptest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class CardAdapter(private val restaurantList: MutableList<Restaurants>) : RecyclerView.Adapter<CardAdapter.CardHolder>()
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
        holder.restaDescr.text = currentItem.restaDescr

        // Remove later
        holder.restaMark.text = currentItem.mark

        // Expand card on click
        val isExpanded: Boolean = currentItem.expanded
        holder.relativeLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.card.setOnClickListener {
            currentItem.expanded = !currentItem.expanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val restaImage: ShapeableImageView = itemView.findViewById(R.id.rest_image)
        val restaName: TextView = itemView.findViewById(R.id.rest_name)
        val restaInfo: TextView = itemView.findViewById(R.id.rest_info)
        val restaDescr: TextView = itemView.findViewById(R.id.rest_desc)
        val relativeLayout: RelativeLayout = itemView.findViewById(R.id.expanded_view)
        val card: CardView = itemView.findViewById(R.id.card)

        // Remove later
        val restaMark: TextView = itemView.findViewById(R.id.rest_mark)
    }
}