package com.example.plantly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rvPlantsList.layoutManager = LinearLayoutManager(this)
        val plantAdapter = PlantAdapter(this, getPlantsList())
        rvPlantsList.adapter = plantAdapter

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, PlantFormActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        }

    }
    private fun getPlantsList(): ArrayList<Plant> {
        val list = ArrayList<Plant>()


        return list
    }


}
