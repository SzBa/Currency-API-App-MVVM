package com.example.waluty.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class DBHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        internal const val DATABASE_VERSION = 1
        internal const val DATABASE_NAME = "Currency.db"
        internal const val TABLE_NAME = "currency"
        internal const val TABLE_NAME2 = "favourite"
        internal const val COL_CURRENCY = "name"
        internal const val COL_CODE = "code"
        internal const val COL_MID = "mid"
        internal const val COL_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                    "$COL_CURRENCY TEXT NOT NULL, " +
                    "$COL_CODE TEXT NOT NULL, " +
                    "$COL_MID DOUBLE NOT NULL, " +
                    "$COL_DATE TEXT NOT NULL)"
        )
        db!!.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME2 (" +
                    "$COL_CURRENCY TEXT NOT NULL, " +
                    "$COL_CODE TEXT NOT NULL, " +
                    "$COL_MID DOUBLE NOT NULL, " +
                    "$COL_DATE TEXT NOT NULL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME2")
        onCreate(db!!)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    val allCurrency: MutableList<ConcreteValue>

        get() {
            val query = "SELECT * FROM $TABLE_NAME"
            val currencyy = mutableListOf<ConcreteValue>()
            val db = this.writableDatabase
            val cursor = db.rawQuery(query, null)

            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex(COL_CURRENCY))
                    val code = cursor.getString(cursor.getColumnIndex(COL_CODE))
                    val mid = cursor.getDouble(cursor.getColumnIndex(COL_MID))
                    val date = cursor.getString(cursor.getColumnIndex(COL_DATE))

                    val concreteValue = ConcreteValue(name, code, mid,date)
                    currencyy.add(concreteValue)

                } while (cursor.moveToNext())

            }
            db.close()
            return currencyy
        }
    val allFavourite: MutableList<ConcreteValue>

        get() {
            val query = "SELECT * FROM $TABLE_NAME2 ORDER BY $COL_MID DESC"
            val currencyy = mutableListOf<ConcreteValue>()
            val db = this.writableDatabase
            val cursor = db.rawQuery(query, null)

            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex(COL_CURRENCY))
                    val code = cursor.getString(cursor.getColumnIndex(COL_CODE))
                    val mid = cursor.getDouble(cursor.getColumnIndex(COL_MID))
                    val date = cursor.getString(cursor.getColumnIndex(COL_DATE))

                    val concreteValue = ConcreteValue(name, code, mid,date)
                    currencyy.add(concreteValue)

                } while (cursor.moveToNext())

            }
            db.close()
            return currencyy
        }
    fun neededCurrency(code:String):List<ConcreteValue>
    {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_CODE LIKE '$code' ORDER BY $COL_DATE DESC"
        val currencyy = mutableListOf<ConcreteValue>()
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COL_CURRENCY))
                val code = cursor.getString(cursor.getColumnIndex(COL_CODE))
                val mid = cursor.getDouble(cursor.getColumnIndex(COL_MID))
                val date = cursor.getString(cursor.getColumnIndex(COL_DATE))

                val concreteValue = ConcreteValue(name, code, mid,date)
                currencyy.add(concreteValue)

            } while (cursor.moveToNext())

        }
        db.close()
        return currencyy
    }

    fun addCurrency(currency: Currency,date:String) {
        val db = this.writableDatabase

            val value = contentValuesOf()
                value.put(COL_CURRENCY, currency.currency)
                value.put(COL_CODE, currency.code)
                value.put(COL_MID, currency.mid.toString())
                value.put(COL_DATE, date)
                db.insert(TABLE_NAME, null, value)
            db.close()
    }
    fun addFavourite(currency: ConcreteValue) {
        val db = this.writableDatabase

        val value = contentValuesOf()
        value.put(COL_CURRENCY, currency.name)
        value.put(COL_CODE, currency.code)
        value.put(COL_MID, currency.mid.toString())
        value.put(COL_DATE, currency.date)
        db.insert(TABLE_NAME2, null, value)
        db.close()
    }
    fun deleteFavourite(code:String,date:String)
    {
        val db = this.writableDatabase

        db.delete(TABLE_NAME2,"$COL_CODE=? AND $COL_DATE=?",arrayOf(code,date))
    }


    fun CheckIfDataAlreadyInDBorNot(currency: List<Currency>, date: String) {
        for (i in 0..currency.size - 1) {
            var code = currency[i].code
            val db = this.writableDatabase
            val query = "SELECT * FROM $TABLE_NAME WHERE $COL_CODE = '$code' AND $COL_DATE = '$date'"
            val cursor = db.rawQuery(query, null)
            if (cursor.count <= 0) {
                cursor.close()
                addCurrency(currency[i], date)
            }
            cursor.close()
        }
        return
    }
    fun CheckIfFavIsAlreadyInDB(currency:ConcreteValue) {
            var code = currency.code
            var date:String = currency.date
            val db = this.writableDatabase
            val query = "SELECT * FROM $TABLE_NAME2 WHERE $COL_CODE = '$code' AND $COL_DATE = '$date'"
            val cursor = db.rawQuery(query, null)
            if (cursor.count <= 0) {
                cursor.close()
                addFavourite(currency)
            }
            cursor.close()
            return
    }

}