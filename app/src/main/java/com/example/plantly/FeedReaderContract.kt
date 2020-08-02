package com.example.plantly

import android.provider.BaseColumns

object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "plant"
        const val COLUMN_NAME_PHOTO_PATH = "photoPath"
        const val COLUMN_NAME_PLANT_NAME = "plantName"
        const val COLUMN_NAME_DAYS_TILL_WATER = "daysTillWater"
        const val COLUMN_NAME_DAYS_TILL_WATER_COUNTDOWN = "daysTillWaterCountdown"
    }
}
