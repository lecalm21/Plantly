package com.example.plantly

import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getPlantsList(): ArrayList<Plant> {
        val dbHelper = FeedReaderDbHelper(applicationContext)
        val db = dbHelper.readableDatabase
        val projection = arrayOf(BaseColumns._ID,
            FeedReaderContract.FeedEntry.COLUMN_NAME_PHOTO_PATH,
            FeedReaderContract.FeedEntry.COLUMN_NAME_PLANT_NAME,
            FeedReaderContract.FeedEntry.COLUMN_NAME_DAYS_TILL_WATER,
            FeedReaderContract.FeedEntry.COLUMN_NAME_DAYS_TILL_WATER_COUNTDOWN)
        val cursor = db.query(
            FeedReaderContract.FeedEntry.TABLE_NAME, projection, null, null,
            null, null, null
        )
        val list = ArrayList<Plant>()
        with(cursor) {
            while (moveToNext()) {
                val plant = Plant(getInt(getColumnIndex(BaseColumns._ID)),
                    getString(getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_PHOTO_PATH)),
                    getString(getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_PLANT_NAME)),
                    getInt(getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_DAYS_TILL_WATER)),
                    getInt(getColumnIndex(
                        FeedReaderContract.FeedEntry.COLUMN_NAME_DAYS_TILL_WATER_COUNTDOWN)
                    ))
                list.add(plant)
            }
        }
        return list
    }
}
