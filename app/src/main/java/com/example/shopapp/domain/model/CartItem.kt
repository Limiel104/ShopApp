package com.example.shopapp.domain.model

data class CartItem(
    val cartItemId: String = "",
    val userUID: String = "",
    val productId: Int = -1,
    val amount: Int = 0
)