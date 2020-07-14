package com.example.plantly

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_plant_form.*
import kotlinx.android.synthetic.main.water_reminder_dialog_fragment.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PlantFormActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var currentPhotoPath: String
    var numberWaterDays : Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_form)

        btnAddPlant.setOnClickListener {
            val plantName: String = tiPlantName.text.toString()
            //DB connection
            val dbHelper = FeedReaderDbHelper(applicationContext)
            // Gets the data repository in write mode
            val db = dbHelper.writableDatabase

            // Create a new map of values, where column names are the keys
            val values = ContentValues().apply {
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_PHOTO_PATH, currentPhotoPath)
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_PLANT_NAME, plantName)
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_DAYS_TILL_WATER, numberWaterDays)
            }

            // Insert the new row, returning the primary key value of the new row
            val newRowId = db?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)

            val intent = Intent(this@PlantFormActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        //Won't use this now. maybe later
        /*
        cvWaterReminder.setOnClickListener() {
            val fm = supportFragmentManager
            val waterReminder = WaterReminderDialogFragment()
            waterReminder.show(fm, "dialog")
        }
         */
        tvNumberWaterDays.text = numberWaterDays.toString()

        btnAddDays.setOnClickListener() {
            numberWaterDays ++
            tvNumberWaterDays.text = numberWaterDays.toString()
        }

        btnRemoveDays.setOnClickListener() {
            if (numberWaterDays == 1)
            else {
                numberWaterDays --
                tvNumberWaterDays.text = numberWaterDays.toString()
            }
        }

        ivTakePhoto.setOnClickListener {
           dispatchTakePictureIntent()
        }


    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.d(TAG, ex.toString())
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.plantly.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(this,
                "Photo file is saved",
                Toast.LENGTH_SHORT).show()
            ivTakePhoto.setImageDrawable(Drawable.createFromPath(currentPhotoPath))
        } else Toast.makeText(this,
            "Photo file can't be saved, please try again",
            Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        val intent = Intent(this@PlantFormActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    companion object {
        private val TAG = PlantFormActivity::class.qualifiedName
    }
}
