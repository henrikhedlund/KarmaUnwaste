package com.fofism.karmaunwaste.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues



class ProductDbHelper(context: Context) : SQLiteOpenHelper(context, ProductItemContract.DB_NAME, null, ProductItemContract.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE " + ProductItemContract.ProductEntry.TABLE + " ( " +
                ProductItemContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProductItemContract.ProductEntry.Food_item + " TEXT NOT NULL, " +
                ProductItemContract.ProductEntry.Used + " BOOLEAN NOT NULL DEFAULT FALSE, " +
                ProductItemContract.ProductEntry.Purchase_Date + " TEXT NULL, " +
                ProductItemContract.ProductEntry.Quantity + " INTEGER NOT NULL DEFAULT 1) "

        db.execSQL(createTable)

        val values = ContentValues()
        values.put(ProductItemContract.ProductEntry.Food_item, "milk")
        values.put(ProductItemContract.ProductEntry.Used, false)
        values.put(ProductItemContract.ProductEntry.Purchase_Date, "20191025")

        val values2 = ContentValues()
        values2.put(ProductItemContract.ProductEntry.Food_item, "salmon")
        values2.put(ProductItemContract.ProductEntry.Used, false)
        values2.put(ProductItemContract.ProductEntry.Purchase_Date, "20191019")

        val values3 = ContentValues()
        values3.put(ProductItemContract.ProductEntry.Food_item, "tomatoes")
        values3.put(ProductItemContract.ProductEntry.Used, false)
        values3.put(ProductItemContract.ProductEntry.Purchase_Date, "20191030")

        val values4 = ContentValues()
        values4.put(ProductItemContract.ProductEntry.Food_item, "eggs")
        values4.put(ProductItemContract.ProductEntry.Used, false)
        values4.put(ProductItemContract.ProductEntry.Purchase_Date, "20191026")

        val values5 = ContentValues()
        values5.put(ProductItemContract.ProductEntry.Food_item, "bread")
        values5.put(ProductItemContract.ProductEntry.Used, false)
        values5.put(ProductItemContract.ProductEntry.Purchase_Date, "20191021")

        val values6 = ContentValues()
        values6.put(ProductItemContract.ProductEntry.Food_item, "cheese")
        values6.put(ProductItemContract.ProductEntry.Used, false)
        values6.put(ProductItemContract.ProductEntry.Purchase_Date, "20191024")

        db.insert(
                ProductItemContract.ProductEntry.TABLE,
                ProductItemContract.ProductEntry._ID,
                values)

        db.insert(
                ProductItemContract.ProductEntry.TABLE,
                ProductItemContract.ProductEntry._ID,
                values2)

        db.insert(
                ProductItemContract.ProductEntry.TABLE,
                ProductItemContract.ProductEntry._ID,
                values3)

        db.insert(
                ProductItemContract.ProductEntry.TABLE,
                ProductItemContract.ProductEntry._ID,
                values4)

        db.insert(
                ProductItemContract.ProductEntry.TABLE,
                ProductItemContract.ProductEntry._ID,
                values5)

        db.insert(
                ProductItemContract.ProductEntry.TABLE,
                ProductItemContract.ProductEntry._ID,
                values6)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + ProductItemContract.ProductEntry.TABLE)
        onCreate(db)
    }
}