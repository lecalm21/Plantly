package com.example.plantly

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class FeedReaderDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${FeedReaderContract.FeedEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${FeedReaderContract.FeedEntry.COLUMN_NAME_PHOTO_PATH} TEXT," +
                "${FeedReaderContract.FeedEntry.COLUMN_NAME_PLANT_NAME} TEXT," +
                "${FeedReaderContract.FeedEntry.COLUMN_NAME_DAYS_TILL_WATER} INTEGER," +
                "${FeedReaderContract.FeedEntry.COLUMN_NAME_DAYS_TILL_WATER_COUNTDOWN} INTEGER)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun delete(id: Int) {
        val db = this.writableDatabase
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs)
    }

    fun water(id: Int) {
        val daysTillWaterRefresh: Int = getDaysTillWater(id)
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_DAYS_TILL_WATER_COUNTDOWN, daysTillWaterRefresh)
        }
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        val count = db.update(
            FeedReaderContract.FeedEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs)
    }

    private fun getDaysTillWater(id: Int): Int {
        val db = this.readableDatabase

        val projection = arrayOf(BaseColumns._ID,
            FeedReaderContract.FeedEntry.COLUMN_NAME_PHOTO_PATH,
            FeedReaderContract.FeedEntry.COLUMN_NAME_PLANT_NAME,
            FeedReaderContract.FeedEntry.COLUMN_NAME_DAYS_TILL_WATER,
            FeedReaderContract.FeedEntry.COLUMN_NAME_DAYS_TILL_WATER_COUNTDOWN)
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, selection,
            selectionArgs, null, null, null
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
        return list[0].daysTillWater
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
    }
}
