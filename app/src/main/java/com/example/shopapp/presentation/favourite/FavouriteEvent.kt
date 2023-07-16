package com.example.shopapp.presentation.favourite

sealed class FavouriteEvent {
    data class OnProductSelected(val value: String): FavouriteEvent()
    object OnLogin: FavouriteEvent()
    object OnSignup: FavouriteEvent()
}