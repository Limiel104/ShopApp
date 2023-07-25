package com.example.shopapp.presentation.favourite

data class FavouriteState (
    val productList: List<String> = emptyList(),
    val productId: String = "",
    val isUserLoggedIn: Boolean = false
)