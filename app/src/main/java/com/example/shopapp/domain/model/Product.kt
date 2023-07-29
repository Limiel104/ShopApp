package com.example.shopapp.domain.model

data class Product(
    val id: Int,
    val title: String,
    val price: String,
    val description: String,
    val category: String,
    val imageUrl: String,
    val isInFavourites: Boolean
)