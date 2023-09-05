package com.example.shopapp.presentation.cart

import com.example.shopapp.domain.model.CartItem

data class CartState(
    val id: String = "",
    val cartItems: List<CartItem> = emptyList(),
    val isUserLoggedIn: Boolean = false,
    val userUID: String = "",
)