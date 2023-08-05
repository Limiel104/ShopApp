package com.example.shopapp.domain.model

data class Favourite(
    val favouriteId: String = "",
    val userUID: String = "",
    val productId: Int = -1
)