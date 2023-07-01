package com.example.shopapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ProductDao {

    @Upsert
    suspend fun upsertAllProducts(products: List<ProductEntity>)

    @Query("DELETE FROM productentity")
    suspend fun deleteAll()
}