package com.example.shopapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: String,
    val description: String,
    val category: String,
    val imageUrl: String
)