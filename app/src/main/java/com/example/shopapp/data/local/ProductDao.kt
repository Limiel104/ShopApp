package com.example.shopapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM productentity")
    suspend fun getProducts(): List<ProductEntity>

    @Query("DELETE FROM productentity")
    suspend fun deleteProducts()
}