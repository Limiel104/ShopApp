package com.example.shopapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class],
    version = 1
)
abstract class ShopDatabase: RoomDatabase() {

    abstract val productDao: ProductDao

    companion object {
        const val DATABASE_NAME = "shop.db"
    }
}