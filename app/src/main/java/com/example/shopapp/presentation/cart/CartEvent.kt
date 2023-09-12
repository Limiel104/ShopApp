package com.example.shopapp.presentation.cart

sealed class CartEvent {
    object OnLogin: CartEvent()
    object OnSignup: CartEvent()
    data class OnPlus(val value: Int): CartEvent()
    data class OnMinus(val value: Int): CartEvent()
    object OnGoBack: CartEvent()
    data class OnDelete(val value: Int): CartEvent()
}