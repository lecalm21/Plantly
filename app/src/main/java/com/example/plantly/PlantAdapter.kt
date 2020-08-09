package com.example.plantly

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
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
        holder.editPlant.tag = plant.id
        holder.daysLeftNumber.text = plant.daysTillWaterCountdown.toString()
        holder.btnWaterPlant.tag = plant.id
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
        val cardTvPlant: TextView = view.cardTvPlant
        val cardIvPlant: ImageView = view.cardIvPlant
        val deletePlant: ImageButton = view.deletePlant
        val editPlant: ImageButton = view.editPlant
        val daysLeftNumber: TextView = view.daysLeftNumber
        val btnWaterPlant: Button = view.btnWaterPlant

        init {
            deletePlant.setOnClickListener(this)
            editPlant.setOnClickListener(this)
            btnWaterPlant.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                when (v.id) {
                    R.id.deletePlant -> deletePlant(v)
                    R.id.editPlant -> editPlant(v)
                    R.id.btnWaterPlant -> waterPlant(v)
                }
            }
        }
        private fun deletePlant(v: View?) {
            val idPlant = v?.tag as Int
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    dbHelper.delete(idPlant)
                    plants.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
        private fun editPlant(v: View?) {
            val id = v?.tag as Int
            val intent = Intent(v.context, PlantEditActivity::class.java)
            intent.putExtra("arg", id)
            v.context.startActivity(intent)
            (context as Activity).finish()
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        private fun waterPlant(v: View?) {
            val id = v?.tag as Int
            dbHelper.water(id)
            notifyDataSetChanged()
        }
    }
}
