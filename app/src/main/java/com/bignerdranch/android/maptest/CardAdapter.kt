package com.bignerdranch.android.maptest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import models.Database

class CardAdapter(private val restaurantList: MutableList<Restaurants>, private val data: Database) : RecyclerView.Adapter<CardAdapter.CardHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_cell, parent, false)
        return CardHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val currentItem = restaurantList[position]

        dataToCard(holder, currentItem)
        setRestaurantMark(holder, currentItem)
        expandCard(holder, currentItem, position)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    @SuppressLint("SetTextI18n")
    private fun dataToCard(holder: CardHolder, currentItem: Restaurants) {
        // Fill cards with restaurant data
        Picasso.get().load(currentItem.restaImage).placeholder(R.drawable.example_image).into(holder.restaImage)
        holder.restaName.text = currentItem.restaName
        holder.restaInfo.text = currentItem.restaInfo
        holder.restaDescr.text = currentItem.restaDescr
        holder.restaMark.text = "Current mark: ${currentItem.mark}"

        // Initialize dropdown menu with array
        holder.autoTextView.setAdapter(holder.arrayAdapter)
    }

    private fun expandCard(holder: CardHolder, currentItem: Restaurants, position: Int) {
        // Expand card on click
        val isExpanded: Boolean = currentItem.expanded
        holder.expandedCard.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.card.setOnClickListener {
            currentItem.expanded = !currentItem.expanded
            notifyItemChanged(position)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setRestaurantMark(holder: CardHolder, currentItem: Restaurants) {
        holder.submitButton.setOnClickListener {
            val empty = ""
            val chosenMark: String = holder.input.editText?.text.toString()
            if (chosenMark != empty) {
                val currMark = currentItem.mark
                val restaId = currentItem.restaId
                if (chosenMark != currMark) {
                    data.updateRestaurantStatus(chosenMark, restaId)
                    Toast.makeText(holder.itemView.context, "${currentItem.restaName} status set to: $chosenMark", Toast.LENGTH_SHORT).show()
                    holder.restaMark.text = "Current mark: $chosenMark"
                    currentItem.mark = chosenMark
                    for (document in MapsActivity.data.flexibleRestaurantList!!) {
                        if (document.id == restaId) {
                            document.status = chosenMark
                        }
                    }
                }
            }
        }
    }

    inner class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        // Restaurant data related views and layouts
        val restaImage: ShapeableImageView = itemView.findViewById(R.id.rest_image)
        val restaName: TextView = itemView.findViewById(R.id.rest_name)
        val restaInfo: TextView = itemView.findViewById(R.id.rest_info)
        val restaDescr: TextView = itemView.findViewById(R.id.rest_desc)
        val expandedCard: ConstraintLayout = itemView.findViewById(R.id.expanded_view)
        val card: CardView = itemView.findViewById(R.id.card)
        var restaMark: TextView = itemView.findViewById(R.id.rest_mark)

        // Dropdown/Set mark related views and layouts
        private val marks: Array<String> = itemView.resources.getStringArray(R.array.marks)
        val autoTextView: AutoCompleteTextView = itemView.findViewById(R.id.autoCompleteTextView)
        val arrayAdapter = ArrayAdapter(itemView.context, R.layout.dropdown_item, marks)
        val input: TextInputLayout = itemView.findViewById(R.id.dropdown_menu)
        val submitButton: Button = itemView.findViewById(R.id.submit_button)
    }
}