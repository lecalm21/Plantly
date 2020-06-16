package com.example.plantly

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class PlantFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_form)

    }

    override fun onBackPressed() {
        val intent = Intent(this@PlantFormActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}