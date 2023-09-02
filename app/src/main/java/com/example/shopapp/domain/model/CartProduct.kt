package com.example.shopapp.domain.model

data class CartProduct(
    val id: Int,
    val title: String,
    val price: String,
    val imageUrl: String,
    val amount: Int
)