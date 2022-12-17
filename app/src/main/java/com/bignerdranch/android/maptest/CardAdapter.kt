package com.bignerdranch.android.maptest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class CardAdapter(private val restaurantList: MutableList<Restaurants>) : RecyclerView.Adapter<CardAdapter.CardHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_cell, parent, false)
        return CardHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val currentItem = restaurantList[position]
        Picasso.get().load(currentItem.restaImage).placeholder(R.drawable.example_image).into(holder.restaImage)
        holder.restaName.text = currentItem.restaName
        holder.restaInfo.text = currentItem.restaInfo
        holder.restaDescr.text = currentItem.restaDescr

        // Remove later
        holder.restaMark.text = currentItem.mark

        // Dropdown
        holder.autoTextView.setAdapter(holder.arrayAdapter)

        // Expand card on click
        val isExpanded: Boolean = currentItem.expanded
        holder.expandedCard.visibility = if (isExpanded) View.VISIBLE else View.GONE
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
        val expandedCard: ConstraintLayout = itemView.findViewById(R.id.expanded_view)
        val card: CardView = itemView.findViewById(R.id.card)

        // Dropdown
        val autoTextView = itemView.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val marks = itemView.resources.getStringArray(R.array.marks)
        val arrayAdapter = ArrayAdapter(itemView.context, R.layout.dropdown_item, marks)

        // Remove later
        val restaMark: TextView = itemView.findViewById(R.id.rest_mark)
    }
}