package com.example.plantly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rvPlantsList.layoutManager = LinearLayoutManager(this)
        val plantAdapter = PlantAdapter(this, getPlantsList())
        rvPlantsList.adapter = plantAdapter

    }
    private fun getPlantsList(): ArrayList<String> {
        val list = ArrayList<String>()

        list.add("Plant One")
        list.add("Plant Two")
        list.add("Plant Three")
        list.add("Plant Four")
        list.add("Plant Five")
        list.add("Plant Six")
        list.add("Plant Seven")
        list.add("Plant Eight")
        list.add("Plant Nine")
        list.add("Plant Ten")
        list.add("Plant Eleven")
        return list
    }
}
