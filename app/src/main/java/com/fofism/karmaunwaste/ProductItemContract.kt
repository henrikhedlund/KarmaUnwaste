package com.fofism.karmaunwaste.db

import android.provider.BaseColumns

class ProductItemContract {
    companion object {
        val DB_NAME = "com.fofism.todo.db"
        val DB_VERSION = 6
    }

    class ProductEntry : BaseColumns {

        companion object {
            val TABLE = "products"
            val _ID = BaseColumns._ID
            val Food_item = "title"
            val Purchase_Date = "expire_date"
            val Used = "item_is_used"
            val Quantity = "quantity"
        }
    }
}