package com.example.shopapp.presentation.favourites

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.model.Product

data class FavouritesState (
    val productList: List<Product> = emptyList(),
    val productId: Int = -1,
    val isUserLoggedIn: Boolean = false,
    val userUID: String = "",
    val isLoading: Boolean = false,
    val favouriteList: List<Favourite> = emptyList()
)