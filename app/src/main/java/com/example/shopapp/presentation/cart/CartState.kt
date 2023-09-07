package com.example.shopapp.presentation.cart

import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.model.CartProduct

data class CartState(
    val id: String = "",
    val cartItems: List<CartItem> = emptyList(),
    val cartProducts: List<CartProduct> = emptyList(),
    val isUserLoggedIn: Boolean = false,
    val userUID: String = "",
    val isLoading: Boolean = false,
    val totalAmount: Double = -1.0
)