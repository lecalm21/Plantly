package com.example.plantly

import android.provider.BaseColumns

object dbPlantValues : BaseColumns{
    val DATABASE_NAME = "plantdb"
    val DATABASE_VERSION = 1
    //specific for table
    const val TABLE_NAME = "plant"
    const val COLUMN_NAME_PHOTO_PATH = "photoPath"
    const val COLUMN_NAME_PLANT_NAME = "plantName"
    const val COLUMN_NAME_DAYS_TILL_WATER = "daysTillWater"

}