package com.example.shopapp.presentation.product_details

data class ProductDetailsState(
    val productId: Int = -1,
    val title: String = "",
    val price: String = "",
    val description: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val isInFavourites: Boolean = false
)