package com.example.plantly

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.plants_row.view.*


class PlantAdapter(private val context: Context, private val plants: ArrayList<Plant>) :
    RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    private val dbHelper = FeedReaderDbHelper(context)

    override fun onBindViewHolder(holder: PlantAdapter.ViewHolder, position: Int) {
        val plant = plants[position]

        holder.cardIvPlant.setImageDrawable(Drawable.createFromPath(plant.photoPath))
        holder.cardTvPlant.text = plant.plantName
        holder.deletePlant.tag = plant.id

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.plants_row, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return plants.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val cardTvPlant:TextView = view.cardTvPlant
        val cardIvPlant:ImageView = view.cardIvPlant
        val deletePlant:ImageButton = view.deletePlant

        init {
            deletePlant.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val id = v?.tag as Int
            dbHelper.delete(id)
            plants.removeAt(adapterPosition)
            notifyItemRemoved(adapterPosition)
        }
    }
}




