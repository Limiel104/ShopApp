package com.example.shopapp.presentation.cart

sealed class CartEvent {
    object OnLogin: CartEvent()
    object OnSignup: CartEvent()
}