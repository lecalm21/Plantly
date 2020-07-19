package com.example.plantly

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.plants_row.view.*
import androidx.appcompat.app.AppCompatActivity

class PlantAdapter(val context: Context, val plants: ArrayList<Plant>) :
    RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: PlantAdapter.ViewHolder, position: Int) {
        val plant = plants[position]

        holder.cardIvPlant.setImageDrawable(Drawable.createFromPath(plant.photoPath))
        holder.cardTvPlant.text = plant.plantName
        holder.databaseID.text = plant.id.toString()
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
        val cardTvPlant = view.cardTvPlant
        val cardIvPlant = view.cardIvPlant
        val databaseID = view.databaseID
    }
}

