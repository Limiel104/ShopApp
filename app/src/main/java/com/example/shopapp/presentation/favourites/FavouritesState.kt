package com.example.shopapp.presentation.favourites

data class FavouritesState (
    val productList: List<String> = emptyList(),
    val productId: String = "",
    val isUserLoggedIn: Boolean = false
)