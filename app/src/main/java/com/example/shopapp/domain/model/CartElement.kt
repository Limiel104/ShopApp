package com.example.shopapp.domain.model

data class CartElement(
    val cartElementId: String = "",
    val userUID: String = "",
    val productId: Int = -1,
    val amount: Int = 0
)