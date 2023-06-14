package com.example.shopapp.presentation.favourites

data class FavouriteState (
    val productList: List<String> = emptyList(),
    val productId: String = ""
)