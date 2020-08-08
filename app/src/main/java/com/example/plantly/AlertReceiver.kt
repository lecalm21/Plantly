package com.example.plantly

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.provider.BaseColumns
import androidx.core.app.NotificationCompat

class AlertReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val channelId = "1"
        val plantList: ArrayList<Plant> = getPlantsList(context)
        for (plant in plantList) {
            var newCountdown: Int = 0
            if (plant.daysTillWaterCountdown > 0) {
                newCountdown = plant.daysTillWaterCountdown - 1
                val dbHelper = FeedReaderDbHelper(context)
                val db = dbHelper.writableDatabase
                val values = ContentValues().apply {
                    put(FeedReaderContract.FeedEntry.COLUMN_NAME_DAYS_TILL_WATER_COUNTDOWN, newCountdown)
                }
                val selection = "${BaseColumns._ID} = ?"
                val selectionArgs = arrayOf(plant.id.toString())
                val count = db.update(
                    FeedReaderContract.FeedEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs)
            } else {
                var builder = NotificationCompat.Builder(context, channelId)
                    // .setSmallIcon(R.drawable.)
                    .setContentTitle("One of your plants needs water")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            }
        }
    }

    private fun getPlantsList(context: Context): ArrayList<Plant> {
        val dbHelper = FeedReaderDbHelper(context)
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_PHOTO_PATH,
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
                    getInt(getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_DAYS_TILL_WATER_COUNTDOWN)))
                list.add(plant)
            }
        }
        return list
    }
}
