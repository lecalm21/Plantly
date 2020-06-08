package com.example.plantly

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.plants_row.view.*

class PlantAdapter(val context: Context, val plants: ArrayList<String>) :
    RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: PlantAdapter.ViewHolder, position: Int) {
        val plant = plants[position]

        holder.tvPlant.text = plant

        if (position % 2 == 0) {
            holder.tvPlant.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorLightGray
                )
            )
        } else {
            holder.tvPlant.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorWhite
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.plants_row,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return plants.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlant = view.tvPlant
    }
}